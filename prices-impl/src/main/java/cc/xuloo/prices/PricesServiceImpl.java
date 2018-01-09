package cc.xuloo.prices;

import akka.NotUsed;
import cc.xuloo.betfair.api.PricesService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.concurrent.CompletableFuture;

public class PricesServiceImpl implements PricesService {

    @Override
    public ServiceCall<NotUsed, String> hello() {
        return request -> CompletableFuture.completedFuture("hello");
    }
}
