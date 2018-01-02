package cc.xuloo.betfair.impl;

import akka.actor.Actor;
import cc.xuloo.betfair.aping.entities.Event;
import cc.xuloo.betfair.aping.entities.MarketCatalogue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

import java.util.List;

public interface BetfairServiceProtocol {

    class Start {}

    @Value
    class MonitorEvent {

        private final Event event;

        @JsonCreator
        public MonitorEvent(Event event) {
            this.event = event;
        }
    }

    @Value
    class EventMonitored {

        private final List<MarketCatalogue> catalogues;

        @JsonCreator
        public EventMonitored(List<MarketCatalogue> catalogues) {
            this.catalogues = catalogues;
        }
    }
}
