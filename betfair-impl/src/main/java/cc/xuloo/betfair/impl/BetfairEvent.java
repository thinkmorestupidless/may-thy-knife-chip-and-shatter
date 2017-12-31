package cc.xuloo.betfair.impl;

import cc.xuloo.betfair.aping.entities.Event;
import cc.xuloo.betfair.aping.entities.MarketCatalogue;
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

    @Override
    default AggregateEventTagger<BetfairEvent> aggregateTag() {
        return TAG;
    }
}
