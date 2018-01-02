package cc.xuloo.betfair.client.actors;

import akka.actor.ActorRef;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.aping.enums.MarketProjection;
import cc.xuloo.betfair.aping.enums.MarketSort;
import cc.xuloo.betfair.client.BetfairSession;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

public interface ExchangeProtocol {

    @Value
    @Builder
    class Command implements ExchangeProtocol {

        private final BetfairSession session;

        private final ActorRef listener;

        private final ExchangeProtocol command;
    }

    @Value
    @Builder
    class ListEvents implements ExchangeProtocol {

        private final MarketFilter filter;
    }

    @Value
    @Builder
    class ListMarketCatalogues implements ExchangeProtocol {

        private final MarketFilter filter;

        @Singular
        private final Set<MarketProjection> marketProjections;

        private final MarketSort sort;

        private final int maxResults;
    }
}
