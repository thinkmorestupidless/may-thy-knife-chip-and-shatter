package cc.xuloo.prices;

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

public interface PricesEvent extends Jsonable, AggregateEvent<PricesEvent> {

    AggregateEventShards<PricesEvent> TAG = AggregateEventTag.sharded(PricesEvent.class, 4);

    @Value
    @JsonDeserialize
    class FixtureAdded implements PricesEvent {

        private final Event event;

        @JsonCreator
        public FixtureAdded(Event event) {
            this.event = event;
        }
    }

    @Value
    @JsonDeserialize
    class MarketCatalogueAdded implements PricesEvent {

        private final MarketCatalogue catalogue;

        @JsonCreator
        public MarketCatalogueAdded(MarketCatalogue catalogue) {
            this.catalogue = catalogue;
        }
    }

    @Value
    @JsonDeserialize
    class MarketDataAdded implements PricesEvent {

        private final MarketChange data;

        @JsonCreator
        public MarketDataAdded(MarketChange data) {
            this.data = data;
        }
    }

    @Value
    @JsonDeserialize
    class MarketDataMerged implements PricesEvent {

        private final MarketChange data;

        @JsonCreator
        public MarketDataMerged(MarketChange data) {
            this.data = data;
        }
    }

    @Override
    default AggregateEventTagger<PricesEvent> aggregateTag() {
        return TAG;
    }
}
