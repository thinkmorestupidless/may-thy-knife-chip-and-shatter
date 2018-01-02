package cc.xuloo.betfair.impl;

import cc.xuloo.betfair.client.exchange.entities.Event;
import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;
import cc.xuloo.betfair.client.stream.MarketChange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;
import org.assertj.core.util.Sets;

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

    private final Set<MarketState> markets;

    @JsonCreator
    public BetfairState(Event event, Set<MarketState> markets) {
        this.event = event;
        this.markets = markets;
    }

    public BetfairState withEvent(Event event) {
        return new BetfairState(event, markets);
    }

    public BetfairState withMarketCatalogue(MarketCatalogue catalogue) {
        Set<MarketState> markets = new HashSet<>(this.markets);
        Collections.addAll(markets, MarketState.from(catalogue));

        return new BetfairState(event, markets);
    }

    public BetfairState withMarketData(MarketChange data) {
        Set<MarketState> markets = Sets.newHashSet();

        for (MarketState state : this.markets) {
            if (state.getCatalogue().getMarketId().equals(data.getId())) {
                markets.add(state.withData(data));
            } else {
                markets.add(state);
            }
        }

        return new BetfairState(event, markets);
    }
}
