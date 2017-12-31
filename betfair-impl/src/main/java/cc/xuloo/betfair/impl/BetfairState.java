package cc.xuloo.betfair.impl;

import cc.xuloo.betfair.aping.entities.Event;
import cc.xuloo.betfair.aping.entities.MarketCatalogue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Value
@JsonDeserialize
public class BetfairState implements CompressedJsonable {

    public static BetfairState empty() {
        return new BetfairState(new Event("", "", "", "", "", new Date(0)), Collections.emptySet());
    }

    private final Event event;

    private final Set<MarketCatalogue> markets;

    @JsonCreator
    public BetfairState(Event event, Set<MarketCatalogue> markets) {
        this.event = event;
        this.markets = markets;
    }

    public BetfairState withEvent(Event event) {
        return new BetfairState(event, markets);
    }

    public BetfairState withMarketCatalogue(MarketCatalogue catalogue) {
        Set<MarketCatalogue> markets = new HashSet<>(this.markets);
        Collections.addAll(markets, catalogue);

        return new BetfairState(event, markets);
    }
}
