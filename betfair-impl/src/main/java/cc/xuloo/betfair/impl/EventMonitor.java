package cc.xuloo.betfair.impl;

import akka.actor.AbstractActor;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.aping.enums.MarketProjection;
import cc.xuloo.betfair.aping.enums.MarketSort;
import cc.xuloo.betfair.client.BetfairClient;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class EventMonitor extends AbstractActor {

    private final static Logger log = LoggerFactory.getLogger(EventMonitor.class);

    private final BetfairClient betfair;

    private final PersistentEntityRegistry registry;

    @Inject
    public EventMonitor(BetfairClient betfair, PersistentEntityRegistry registry) {
        this.betfair = betfair;
        this.registry = registry;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(BetfairProtocol.MonitorEvent.class, this::monitorEvent).build();
    }

    public void monitorEvent(BetfairProtocol.MonitorEvent cmd) {
        log.info("monitoring event -> {}", cmd.getEvent());

        MarketFilter filter = MarketFilter.builder()
                                          .eventId(cmd.getEvent().getId())
                                          .build();

        Set<MarketProjection> projections = Sets.newHashSet(MarketProjection.COMPETITION, MarketProjection.RUNNER_DESCRIPTION);
        MarketSort sort = MarketSort.MAXIMUM_TRADED;

        betfair.listMarketCatalogue(filter, projections, sort, 1000).thenAccept(catalogues ->
            catalogues.forEach(catalogue -> {
                cc.xuloo.betfair.stream.MarketFilter streamFilter = cc.xuloo.betfair.stream.MarketFilter.builder()
                        .marketId(catalogue.getMarketId())
                        .build();

                betfair.subscribeToMarket(streamFilter, null).thenAccept(response -> {
                    log.info("response -> " + response);
                });
            }));
    }
}
