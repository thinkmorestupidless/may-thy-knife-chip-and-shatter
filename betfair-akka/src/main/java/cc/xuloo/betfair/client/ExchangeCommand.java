package cc.xuloo.betfair.client;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

public interface ExchangeCommand {

    @Value
    class Connect implements ExchangeCommand {

        private final ActorRef listener;

        private final String applicationKey;

        @JsonCreator
        public Connect(ActorRef listener, String applicationKey) {
            this.listener = listener;
            this.applicationKey = applicationKey;
        }
    }
}
