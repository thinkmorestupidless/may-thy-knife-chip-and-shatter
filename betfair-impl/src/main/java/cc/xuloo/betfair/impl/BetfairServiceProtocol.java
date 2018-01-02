package cc.xuloo.betfair.impl;

import cc.xuloo.betfair.client.exchange.entities.Event;
import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;
import com.fasterxml.jackson.annotation.JsonCreator;
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
