package cc.xuloo.fixtures.impl;

import akka.NotUsed;
import cc.xuloo.fixtures.FixturesService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.concurrent.CompletableFuture;

public class FixturesServiceImpl implements FixturesService {

    @Override
    public ServiceCall<NotUsed, String> hello() {
        return request -> CompletableFuture.completedFuture("hello");
    }
}
