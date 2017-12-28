package cc.xuloo.betfair.client.actors;

import cc.xuloo.betfair.client.BetfairSession;
import lombok.Builder;
import lombok.Value;

public interface StreamProtocol {

    @Value
    class Connect implements StreamProtocol {}

    @Value
    @Builder
    class Authenticate implements StreamProtocol {

        private final BetfairSession session;
    }

    @Value
    @Builder
    class MarketSubscription implements StreamProtocol {

        private final String marketId;
    }
}
