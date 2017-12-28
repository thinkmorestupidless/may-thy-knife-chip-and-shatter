package cc.xuloo.betfair.client.actors;

import cc.xuloo.betfair.aping.entities.MarketFilter;
import lombok.Value;

public interface ExchangeProtocol {

    class Login {}

    @Value
    class ListEvents {

        private final MarketFilter filter;
    }
}
