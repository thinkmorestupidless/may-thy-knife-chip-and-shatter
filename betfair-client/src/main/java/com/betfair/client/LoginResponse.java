package com.betfair.client;

import lombok.Value;

@Value
public class LoginResponse {

    private final String sessionToken;

    private final String loginStatus;
}
