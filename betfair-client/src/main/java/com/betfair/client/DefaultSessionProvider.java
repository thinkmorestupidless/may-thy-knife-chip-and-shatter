package com.betfair.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class DefaultSessionProvider implements SessionProvider {

    private final BetfairClient client;

    private BetfairSession session = BetfairSession.loggedOut();

    public DefaultSessionProvider(BetfairClient client) {
        this.client = client;
    }

    @Override
    public CompletionStage<BetfairSession> session() {
        if (session.isLoggedIn()) {
            return CompletableFuture.completedFuture(session);
        }

        return client.login();
    }
}
