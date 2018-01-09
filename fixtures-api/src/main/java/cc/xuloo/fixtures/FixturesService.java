package cc.xuloo.fixtures;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

public interface FixturesService extends Service {

    ServiceCall<NotUsed, String> hello();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("fixtures").withCalls(
            restCall(Method.GET, "/hello", this::hello)
        ).withAutoAcl(true);
        // @formatter:on
    }
}
