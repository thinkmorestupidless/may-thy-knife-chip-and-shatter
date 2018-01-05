package cc.xuloo.betfair.impl;

import cc.xuloo.betfair.client.exchange.entities.Event;
import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;
import cc.xuloo.betfair.client.stream.MarketChange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface BetfairEvent extends Jsonable, AggregateEvent<BetfairEvent> {

    AggregateEventShards<BetfairEvent> TAG = AggregateEventTag.sharded(BetfairEvent.class, 4);

    @Value
    @JsonDeserialize
    class FixtureAdded implements BetfairEvent {

        private final Event event;

        @JsonCreator
        public FixtureAdded(Event event) {
            this.event = event;
        }
    }

    @Value
    @JsonDeserialize
    class MarketCatalogueAdded implements BetfairEvent {

        private final MarketCatalogue catalogue;

        @JsonCreator
        public MarketCatalogueAdded(MarketCatalogue catalogue) {
            this.catalogue = catalogue;
        }
    }

    @Value
    @JsonDeserialize
    class MarketDataAdded implements BetfairEvent {

        private final MarketChange data;

        @JsonCreator
        public MarketDataAdded(MarketChange data) {
            this.data = data;
        }
    }

    @Value
    @JsonDeserialize
    class MarketDataMerged implements BetfairEvent {

        private final MarketChange data;

        @JsonCreator
        public MarketDataMerged(MarketChange data) {
            this.data = data;
        }
    }

    @Override
    default AggregateEventTagger<BetfairEvent> aggregateTag() {
        return TAG;
    }
}
