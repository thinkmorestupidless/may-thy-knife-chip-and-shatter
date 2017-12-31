package cc.xuloo.betfair.impl;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.aping.containers.ListMarketCatalogueContainer;
import cc.xuloo.betfair.aping.entities.MarketCatalogue;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.aping.enums.MarketProjection;
import cc.xuloo.betfair.aping.enums.MarketSort;
import cc.xuloo.betfair.client.actors.ExchangeProtocol;
import cc.xuloo.betfair.stream.MarketSubscriptionMessage;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.pubsub.PubSubRegistry;

import java.util.List;
import java.util.Set;

public class EventMonitor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef betfair;

    private final PersistentEntityRegistry registry;

    @Inject
    public EventMonitor(@Named("betfair-client") ActorRef betfair, PersistentEntityRegistry registry) {
        this.betfair = betfair;
        this.registry = registry;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(BetfairProtocol.MonitorEvent.class, this::monitorEvent)
                .match(ListMarketCatalogueContainer.class, this::handleListMarketCatalogueContainer)
                .build();
    }

    public void monitorEvent(BetfairProtocol.MonitorEvent cmd) {
        log.info("monitoring event -> {}", cmd.getEvent());

        MarketFilter filter = MarketFilter.builder()
                  .eventId(cmd.getEvent().getId())
                  .build();

        Set<MarketProjection> projections = Sets.newHashSet(MarketProjection.COMPETITION, MarketProjection.RUNNER_DESCRIPTION);
        MarketSort sort = MarketSort.MAXIMUM_TRADED;

        ExchangeProtocol protocol = ExchangeProtocol.ListMarketCatalogues.builder()
                .filter(filter)
                .marketProjection(MarketProjection.COMPETITION)
                .marketProjection(MarketProjection.RUNNER_DESCRIPTION)
                .marketProjection(MarketProjection.EVENT)
                .sort(MarketSort.MAXIMUM_TRADED)
                .maxResults(100)
                .build();

        betfair.tell(protocol, getSelf());
    }

    public void handleListMarketCatalogueContainer(ListMarketCatalogueContainer container) {
        if (container.getError() != null) {
            log.warning("error listing market catalogues -> {}", container.getError());
        } else {
            List<MarketCatalogue> catalogues = container.getResult();

            if (catalogues.size() > 0) {
                subscribeToMarket(catalogues.get(0));
            }

//            catalogues.forEach(this::subscribeToMarket);
        }
    }

    public void subscribeToMarket(MarketCatalogue catalogue) {
        PersistentEntityRef<BetfairCommand> entity = registry.refFor(BetfairEntity.class, catalogue.getEvent().getId());
        entity.ask(new BetfairCommand.AddMarketCatalogue(catalogue))
        .thenAccept(done -> {
            cc.xuloo.betfair.stream.MarketFilter streamFilter = cc.xuloo.betfair.stream.MarketFilter.builder()
                    .marketId(catalogue.getMarketId())
                    .build();

            MarketSubscriptionMessage msg = MarketSubscriptionMessage.builder()
                    .marketFilter(streamFilter)
                    .build();

            betfair.tell(msg, getSelf());
        });
    }
}
