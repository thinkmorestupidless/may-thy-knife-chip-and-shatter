package cc.xuloo.betfair.client.exchange;

import cc.xuloo.betfair.client.ApiOperations;

public interface ExchangeOperations extends ApiOperations {

    public static final String LIST_EVENTS              = API_PREFIX + "listEvents";
    public static final String LIST_EVENT_TYPES         = API_PREFIX + "listEventTypes";
    public static final String LIST_MARKET_CATALOGUE    = API_PREFIX + "listMarketCatalogue";
}
