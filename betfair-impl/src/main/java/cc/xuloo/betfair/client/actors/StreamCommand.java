package cc.xuloo.betfair.client.actors;

import cc.xuloo.betfair.client.BetfairSession;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

public interface StreamCommand {

    @Value
    class Connect implements StreamCommand {

        private final BetfairSession session;

        private String host;

        private int port;

        @JsonCreator
        public Connect(BetfairSession session, String host, int port) {
            this.session = session;
            this.host = host;
            this.port = port;
        }
    }
}
