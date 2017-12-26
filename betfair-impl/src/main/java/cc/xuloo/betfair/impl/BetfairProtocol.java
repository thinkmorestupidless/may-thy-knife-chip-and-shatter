package cc.xuloo.betfair.impl;

import akka.actor.Actor;
import cc.xuloo.betfair.aping.entities.Event;
import lombok.Value;

public interface BetfairProtocol {

    class Start {}

    @Value
    class MonitorEvent {

        private final Event event;
    }

    public interface Factory {
        public Actor create();
    }
}
