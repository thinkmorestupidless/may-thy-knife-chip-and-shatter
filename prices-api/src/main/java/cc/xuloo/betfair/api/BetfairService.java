package cc.xuloo.betfair.api;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;

import static com.lightbend.lagom.javadsl.api.Service.named;

public interface BetfairService extends Service {

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("betfair")
                .withAutoAcl(true);
        // @formatter:on
    }
}
