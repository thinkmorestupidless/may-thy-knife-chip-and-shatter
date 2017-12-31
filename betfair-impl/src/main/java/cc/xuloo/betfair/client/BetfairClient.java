package cc.xuloo.betfair.client;

import akka.Done;
import cc.xuloo.betfair.aping.entities.EventTypeResult;
import cc.xuloo.betfair.aping.entities.MarketCatalogue;
import cc.xuloo.betfair.aping.enums.MarketSort;
import cc.xuloo.betfair.aping.entities.EventResult;
import cc.xuloo.betfair.aping.entities.MarketFilter;
import cc.xuloo.betfair.aping.enums.MarketProjection;
import cc.xuloo.betfair.stream.MarketDataFilter;
import cc.xuloo.betfair.stream.ResponseMessage;
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
        exchange = new ExchangeApi(connection);
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
        return exchange.listEventTypes(null, filter).thenApply(response -> response.getResult());
    }

    public CompletionStage<List<EventResult>> listEvents(MarketFilter filter) {
        return exchange.listEvents(null, filter).thenApply(response -> response.getResult());
    }

    public CompletionStage<List<MarketCatalogue>> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection, MarketSort sort, int maxResults) {
        return exchange.listMarketCatalogue(null, filter, marketProjection, sort, maxResults).thenApply(response -> response.getResult());
    }

    public CompletionStage<ResponseMessage> subscribeToMarket(cc.xuloo.betfair.stream.MarketFilter marketFilter, MarketDataFilter marketDataFilter) {
        return stream.subscribe(marketFilter, marketDataFilter);
    }
}
