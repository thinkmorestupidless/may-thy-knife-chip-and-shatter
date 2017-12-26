package cc.xuloo.betfair.impl;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.aping.entities.EventResult;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.BetfairClient;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import play.libs.akka.InjectedActorSupport;

public class BetfairWorker extends AbstractActor implements InjectedActorSupport {

    final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final BetfairClient betfair;

    private final BetfairProtocol.Factory factory;

    @Inject
    public BetfairWorker(BetfairClient betfair, BetfairProtocol.Factory factory) {
        this.betfair = betfair;
        this.factory = factory;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(BetfairProtocol.Start.class, this::start).build();
    }

    private void start(BetfairProtocol.Start cmd) {
        log.info("starting betfair worker");

        MarketFilter filter = MarketFilter.builder()
                                          .eventTypeIds(Sets.newHashSet("1"))
                                          .marketCountries(Sets.newHashSet("GB"))
                                          .build();

        betfair.listEvents(filter)
                .thenAccept(result -> {
                    if (result.size() > 0) {
                        EventResult first = result.get(0);

                        ActorRef monitor = injectedChild(() -> factory.create(), "betfair-worker");
                        monitor.tell(new BetfairProtocol.MonitorEvent(result.get(0).getEvent()), getSelf());
                    }
                });
    }
}
