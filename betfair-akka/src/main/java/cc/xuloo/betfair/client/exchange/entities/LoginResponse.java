package cc.xuloo.betfair.client.exchange.entities;

import lombok.Value;

@Value
public class LoginResponse {

    private final String sessionToken;

    private final String loginStatus;
}
