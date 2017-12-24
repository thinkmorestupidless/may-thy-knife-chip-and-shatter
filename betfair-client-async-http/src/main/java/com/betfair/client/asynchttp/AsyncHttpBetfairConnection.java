package com.betfair.client.asynchttp;

import com.betfair.client.BetfairConnection;
import com.betfair.client.BetfairSession;
import com.typesafe.config.Config;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.concurrent.CompletionStage;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class AsyncHttpBetfairConnection implements BetfairConnection {

    private static final Logger log = LoggerFactory.getLogger(AsyncHttpBetfairConnection.class);

    private final Config conf;

    private AsyncHttpClient httpClient;

    public AsyncHttpBetfairConnection(Config conf) {
        this.conf = conf;
    }

    public SslContext sslContext() {
        try {
            return SslContextBuilder
                                .forClient()
                                .keyManager(new File(conf.getString("betfair.certFile")),
                                            new File(conf.getString("betfair.keyFile")),
                                            conf.getString("betfair.keyPassword"))
                                .build();
        } catch (SSLException e) {
            throw new RuntimeException("Failed to create SSL context", e);
        }
    }

    public AsyncHttpClient httpClient() {
        if (httpClient == null) {
            httpClient = asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder()
                    .setSslContext(sslContext())
                    .setAcceptAnyCertificate(true));
        }

        return httpClient;
    }

    @Override
    public CompletionStage<String> connect() {
        return httpClient()
                .preparePost("https://identitysso.betfair.com/api/certlogin")
                .setHeader("X-Application",conf.getString("betfair.applicationKey"))
                .addFormParam("username", conf.getString("betfair.username"))
                .addFormParam("password", conf.getString("betfair.password"))
                .execute()
                .toCompletableFuture()
                .thenApply(Response::getResponseBody);
    }

    @Override
    public CompletionStage<String> execute(BetfairSession session, String body) {
        log.info("executing {}", body);

        return httpClient()
                    .preparePost("https://api.betfair.com/exchange/betting/json-rpc/v1")
                    .setBody(body)
                    .setHeader("Content-Type", "application/json")
                    .setHeader("Accept", "application/json")
                    .setHeader("Accept-Charset", "UTF-8")
                    .setHeader("Accept-Encoding", "gzip, deflate")
                    .setHeader("X-Application",conf.getString("betfair.applicationKey"))
                    .setHeader("X-Authentication", session.sessionToken())
                    .execute()
                    .toCompletableFuture()
                    .thenApply(Response::getResponseBody);
    }
}
