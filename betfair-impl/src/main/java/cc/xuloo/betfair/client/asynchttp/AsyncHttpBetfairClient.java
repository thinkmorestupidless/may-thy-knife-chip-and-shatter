package cc.xuloo.betfair.client.asynchttp;

import cc.xuloo.betfair.client.BetfairClient;
import cc.xuloo.betfair.client.settings.BetfairCredentials;

public class AsyncHttpBetfairClient extends BetfairClient {

    public AsyncHttpBetfairClient(BetfairCredentials credentials) {
        super(new AsyncHttpBetfairConnection(credentials));
    }
}
