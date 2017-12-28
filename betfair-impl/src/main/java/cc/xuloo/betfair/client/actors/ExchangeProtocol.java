package cc.xuloo.betfair.client.actors;

import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.client.BetfairSession;
import lombok.Builder;
import lombok.Value;

public interface ExchangeProtocol {

    class Login {}

    @Value
    @Builder
    class ListEvents implements ExchangeProtocol {

        private final BetfairSession session;

        private final MarketFilter filter;
    }
}
