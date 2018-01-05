package cc.xuloo.betfair.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

public interface ExchangeEvent {

    @Value
    class Connected implements ExchangeEvent {

        private final BetfairSession session;

        @JsonCreator
        public Connected(BetfairSession session) {
            this.session = session;
        }
    }
}
