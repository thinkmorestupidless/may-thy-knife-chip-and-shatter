package cc.xuloo.betfair.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.Set;

import static com.lightbend.lagom.javadsl.api.Service.named;

public interface PricesService extends Service {

    ServiceCall<NotUsed, String> hello();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("prices").withCalls(
                Service.restCall(Method.GET, "/prices", this::hello)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
