package com.betfair.client.asynchttp;

import com.betfair.client.BetfairClient;
import com.betfair.client.DefaultSessionProvider;
import com.betfair.client.SessionProvider;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AsyncHttpBetfairClient extends BetfairClient {

    public AsyncHttpBetfairClient() {
        this(ConfigFactory.load());
    }

    public AsyncHttpBetfairClient(Config conf) {
        super(new AsyncHttpBetfairConnection(conf));
    }
}
