package cc.xuloo.betfair.client.actors;

import lombok.Value;

public interface StreamProtocol {

    class Connect {}

    class Authenticate {}

    @Value
    class MarketSubscription {

        private final String marketId;
    }
}
