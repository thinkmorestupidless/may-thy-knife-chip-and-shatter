package cc.xuloo.betfair.client;

import java.util.concurrent.CompletionStage;

public interface SessionProvider {

    CompletionStage<BetfairSession> session();
}
