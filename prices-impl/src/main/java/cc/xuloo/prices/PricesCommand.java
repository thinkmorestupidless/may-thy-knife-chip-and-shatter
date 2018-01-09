package cc.xuloo.prices;

import akka.Done;
import cc.xuloo.betfair.client.exchange.entities.Event;
import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;
import cc.xuloo.betfair.client.stream.MarketChange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface PricesCommand extends Jsonable {

    @Value
    @JsonDeserialize
    class AddFixture implements PricesCommand, PersistentEntity.ReplyType<Done> {

        private final Event event;

        @JsonCreator
        public AddFixture(Event event) {
            this.event = event;
        }
    }

    @Value
    @JsonDeserialize
    class AddMarketCatalogue implements PricesCommand, PersistentEntity.ReplyType<Done> {

        private final MarketCatalogue catalogue;

        @JsonCreator
        public AddMarketCatalogue(MarketCatalogue catalogue) {
            this.catalogue = catalogue;
        }
    }

    @Value
    @JsonDeserialize
    class AddMarketData implements PricesCommand, PersistentEntity.ReplyType<Done> {

        private final MarketChange data;

        @JsonCreator
        public AddMarketData(MarketChange data) {
            this.data = data;
        }
    }

    @Value
    @JsonDeserialize
    class MergeMarketData implements PricesCommand, PersistentEntity.ReplyType<Done> {

        private final MarketChange data;

        @JsonCreator
        public MergeMarketData(MarketChange data) {
            this.data = data;
        }
    }
 }
