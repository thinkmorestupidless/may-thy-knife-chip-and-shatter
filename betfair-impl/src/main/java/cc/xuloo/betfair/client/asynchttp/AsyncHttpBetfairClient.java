package cc.xuloo.betfair.client.asynchttp;

import cc.xuloo.betfair.client.BetfairClient;
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
