package com.betfair.client;

import akka.Done;
import com.betfair.aping.entities.EventResult;
import com.betfair.aping.entities.EventTypeResult;
import com.betfair.aping.entities.MarketCatalogue;
import com.betfair.aping.entities.MarketFilter;
import com.betfair.aping.enums.MarketProjection;
import com.betfair.aping.enums.MarketSort;
import com.betfair.stream.model.MarketDataFilter;
import com.betfair.stream.model.ResponseMessage;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;

public class BetfairClient implements SessionProvider {

    private static final Logger log = LoggerFactory.getLogger(BetfairClient.class);

    private final Config conf = ConfigFactory.load();

    private final SessionProvider sessionProvider;

    private final ExchangeApi exchange;

    private final BetfairStream stream;

    public BetfairClient(BetfairConnection connection) {
        sessionProvider = new DefaultSessionProvider(this);
        exchange = new ExchangeApi(sessionProvider, connection);
        stream = new BetfairStream(sessionProvider, conf);
    }

    @Override
    public CompletionStage<BetfairSession> session() {
        return sessionProvider.session();
    }

    public CompletionStage<Done> stream() {
        return stream.connect();
    }

    public CompletionStage<BetfairSession> login() {
        return exchange.login().thenApply(response -> BetfairSession.loggedIn(response.getSessionToken(), conf.getString("betfair.applicationKey")));
    }

    public CompletionStage<List<EventTypeResult>> listEventTypes(MarketFilter filter) {
        return exchange.listEventTypes(filter).thenApply(response -> response.getResult());
    }

    public CompletionStage<List<EventResult>> listEvents(MarketFilter filter) {
        return exchange.listEvents(filter).thenApply(response -> response.getResult());
    }

    public CompletionStage<List<MarketCatalogue>> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection, MarketSort sort, int maxResults) {
        return exchange.listMarketCatalogue(filter, marketProjection, sort, maxResults).thenApply(response -> response.getResult());
    }

    public CompletionStage<ResponseMessage> subscribeToMarket(com.betfair.stream.model.MarketFilter marketFilter, MarketDataFilter marketDataFilter) {
        log.info("subscribing to market {}", marketFilter);
        return stream.subscribe(marketFilter, marketDataFilter);
    }
}
