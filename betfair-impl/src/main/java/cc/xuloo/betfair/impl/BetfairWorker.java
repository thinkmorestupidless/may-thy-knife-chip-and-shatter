package cc.xuloo.betfair.impl;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.aping.containers.EventResultContainer;
import cc.xuloo.betfair.aping.entities.EventResult;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.actors.ExchangeProtocol;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.typesafe.config.Config;
import play.libs.akka.InjectedActorSupport;

import java.util.List;

public class BetfairWorker extends AbstractActor implements InjectedActorSupport {

    final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final Config config;

    private final ActorRef betfair;

    private final BetfairProtocol.Factory factory;

    @Inject
    public BetfairWorker(Config config, @Named("betfair-client") ActorRef betfair, BetfairProtocol.Factory factory) {
        this.config = config;
        this.betfair = betfair;
        this.factory = factory;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BetfairProtocol.Start.class, this::start)
                .match(EventResultContainer.class, this::handleEventResultContainer)
                .matchAny(o -> log.info("i don't know what to do with {}", o))
                .build();
    }

    private void start(BetfairProtocol.Start cmd) {
        log.info("starting betfair worker");

        MarketFilter filter = MarketFilter.builder()
                                          .eventTypeIds(Sets.newHashSet("1"))
                                          .marketCountries(Sets.newHashSet("GB"))
                                          .build();

        betfair.tell(ExchangeProtocol.ListEvents
                .builder()
                .filter(filter)
                .build(), getSelf());
    }

    private void handleEventResultContainer(EventResultContainer container) {
        if (container.getError() != null) {
            log.warning("error listing events -> {}", container.getError());
        } else {

            List<EventResult> result = container.getResult();

            log.info("result -> {}", result);

            if (result.size() > 0) {
                EventResult first = result.get(0);

                ActorRef monitor = injectedChild(() -> factory.create(), "betfair-worker");
                monitor.tell(new BetfairProtocol.MonitorEvent(result.get(0).getEvent()), getSelf());
            }
        }
    }
}
