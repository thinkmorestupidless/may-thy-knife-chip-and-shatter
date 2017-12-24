package com.betfair.client;

import com.betfair.client.BetfairSession;

import java.util.concurrent.CompletionStage;

public interface SessionProvider {

    CompletionStage<BetfairSession> session();
}
