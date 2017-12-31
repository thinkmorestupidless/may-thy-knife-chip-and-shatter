package cc.xuloo.betfair.impl;

import akka.Done;
import cc.xuloo.betfair.aping.entities.Event;
import cc.xuloo.betfair.aping.entities.MarketCatalogue;
import cc.xuloo.betfair.stream.MarketChange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

public interface BetfairCommand extends Jsonable {

    @Value
    @JsonDeserialize
    class AddFixture implements BetfairCommand, PersistentEntity.ReplyType<Done> {

        private final Event event;

        @JsonCreator
        public AddFixture(Event event) {
            this.event = event;
        }
    }

    @Value
    @JsonDeserialize
    class AddMarketCatalogue implements BetfairCommand, PersistentEntity.ReplyType<Done> {

        private final MarketCatalogue catalogue;

        @JsonCreator
        public AddMarketCatalogue(MarketCatalogue catalogue) {
            this.catalogue = catalogue;
        }
    }
}
