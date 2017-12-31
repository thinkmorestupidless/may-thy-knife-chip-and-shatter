package cc.xuloo.betfair.impl;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.SmallestMailboxPool;
import cc.xuloo.betfair.aping.containers.EventResultContainer;
import cc.xuloo.betfair.aping.entities.Event;
import cc.xuloo.betfair.aping.entities.EventResult;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.actors.ExchangeProtocol;
import com.google.common.collect.Sets;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.typesafe.config.Config;
import play.libs.akka.InjectedActorSupport;

import java.util.List;

public class BetfairWorker extends AbstractActor implements InjectedActorSupport {

    public static Props props(Config config, ActorRef betfair, PersistentEntityRegistry registry) {
        return Props.create(BetfairWorker.class, config, betfair, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final Config config;

    private final ActorRef betfair;

    private final PersistentEntityRegistry registry;

    public BetfairWorker(Config config, ActorRef betfair, PersistentEntityRegistry registry) {
        this.config = config;
        this.betfair = betfair;
        this.registry = registry;
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
        log.debug("starting betfair worker");

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

//            if (result.size() > 0) {
//                EventResult first = result.get(0);
//                handleEvent(first.getEvent());
//            }
            ActorRef router = getContext().actorOf(new SmallestMailboxPool(10).props(EventMonitor.props(betfair, registry)));

            result.forEach(eventResult -> {
                Event event = eventResult.getEvent();
                PersistentEntityRef<BetfairCommand> entity = registry.refFor(BetfairEntity.class, event.getId());
                entity.ask(new BetfairCommand.AddFixture(event))
                        .thenAccept(done -> {
                            router.tell(new BetfairProtocol.MonitorEvent(event), getSelf());
                        });
            });
        }
    }
}
