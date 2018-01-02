package cc.xuloo.betfair.client;

import cc.xuloo.betfair.client.settings.BetfairCredentials;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.util.concurrent.CompletionStage;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class AsyncHttpBetfairConnection implements BetfairConnection {

    private static final Logger log = LoggerFactory.getLogger(AsyncHttpBetfairConnection.class);

    private final BetfairCredentials credentials;

    private AsyncHttpClient httpClient;

    public AsyncHttpBetfairConnection(BetfairCredentials credentials) {
        this.credentials = credentials;
    }

    public SslContext sslContext() {
        try {
            return SslContextBuilder
                                .forClient()
                                .keyManager(credentials.getCertFile(), credentials.getKeyFile(), credentials.getKeyPassword())
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
                .setHeader("X-Application", credentials.getApplicationKey())
                .addFormParam("username", credentials.getUsername())
                .addFormParam("password", credentials.getPassword())
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
                    .setHeader("X-Application", credentials.getApplicationKey())
                    .setHeader("X-Authentication", session.sessionToken())
                    .execute()
                    .toCompletableFuture()
                    .thenApply(Response::getResponseBody);
    }
}
