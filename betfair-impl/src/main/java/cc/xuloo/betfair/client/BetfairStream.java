package cc.xuloo.betfair.client;

import akka.Done;
import cc.xuloo.betfair.stream.*;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

public class BetfairStream {

    private static final Logger log = LoggerFactory.getLogger(BetfairStream.class);

    private final AtomicInteger counter = new AtomicInteger();

    private StreamSocket socket;

    public BetfairStream(SessionProvider sessions, Config config) {
        socket = new StreamSocket(sessions, config);
    }

    public CompletionStage<Done> connect() {
        return socket.authenticate().thenApply(response -> {
            if (response instanceof StatusMessage) {
                StatusMessage status = (StatusMessage) response;

                if (status.getStatusCode().equals("STATUS")) {
                    return Done.getInstance();
                }

                throw new RuntimeException(String.format("Failed to connect -> %s", response));
            }

            throw new RuntimeException(String.format("Failed to connect -> %s", response));
        });
    }

    public CompletionStage<ResponseMessage> subscribe(MarketFilter marketFilter, MarketDataFilter marketDataFilter) {
        return socket.authenticate().thenCompose(response ->
                socket.send(MarketSubscriptionMessage.builder().id(counter.getAndIncrement()).marketFilter(marketFilter).marketDataFilter(marketDataFilter).build()));
    }
}
