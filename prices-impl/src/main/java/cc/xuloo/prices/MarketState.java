package cc.xuloo.prices;

import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;
import cc.xuloo.betfair.client.stream.MarketChange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Value;

@Value
@JsonDeserialize
public class MarketState {

    public static MarketState from(MarketCatalogue catalogue) {
        return new MarketState(catalogue, null);
    }

    private final MarketCatalogue catalogue;

    private final MarketChange data;

    @JsonCreator
    public MarketState(MarketCatalogue catalogue, MarketChange data) {
        this.catalogue = catalogue;
        this.data = data;
    }

    public MarketState withData(MarketChange data) {
        return new MarketState(catalogue, data);
    }
}
