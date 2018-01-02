package cc.xuloo.betfair.impl;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;
import cc.xuloo.betfair.aping.containers.EventResultContainer;
import cc.xuloo.betfair.aping.entities.EventResult;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.actors.ExchangeProtocol;
import cc.xuloo.betfair.stream.MarketSubscriptionMessage;
import com.google.common.collect.Sets;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.typesafe.config.Config;
import play.libs.akka.InjectedActorSupport;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BetfairWorker extends AbstractActor implements InjectedActorSupport {

    public static Props props(Config config, ActorRef betfair, PersistentEntityRegistry registry) {
        return Props.create(BetfairWorker.class, config, betfair, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final Config config;

    private final ActorRef betfair;

    private final PersistentEntityRegistry registry;

    private ActorRef router;

    private int target;

    private Set<String> marketIds;

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

    public Receive handlingMonitoredEvent() {
        return receiveBuilder()
                .match(BetfairProtocol.EventMonitored.class, this::handleEventMonitored)
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

            target = result.size();
            router = getContext().actorOf(new RoundRobinPool(target).props(EventMonitor.props(betfair, registry)));
            marketIds = Sets.newHashSet();

            getContext().become(handlingMonitoredEvent());

            log.info("monitoring markets for {} events", target);

            result.forEach(eventResult ->
                router.tell(new BetfairProtocol.MonitorEvent(eventResult.getEvent()), getSelf()));
        }
    }

    private void handleEventMonitored(BetfairProtocol.EventMonitored msg) {
        marketIds.addAll(msg.getCatalogues().stream().map(catalogue -> catalogue.getMarketId()).collect(Collectors.toSet()));
        target--;

        log.info("waiting for {} catalogues", target);

        if (target == 0) {
            log.info("subscribing to {} markets", marketIds.size());

            Set<String> limited = marketIds.stream().limit(200).collect(Collectors.toSet());

            cc.xuloo.betfair.stream.MarketFilter streamFilter = cc.xuloo.betfair.stream.MarketFilter.builder()
                    .marketIds(limited)
                    .build();

            MarketSubscriptionMessage msm = MarketSubscriptionMessage.builder()
                    .marketFilter(streamFilter)
                    .build();

            betfair.tell(msm, getSelf());
        }
    }
}
