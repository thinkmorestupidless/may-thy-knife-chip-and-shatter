package cc.xuloo.betfair.client;

import java.util.concurrent.CompletionStage;

public interface BetfairConnection {

    CompletionStage<String> connect();

    CompletionStage<String> execute(BetfairSession session, String body);
}
