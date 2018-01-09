package cc.xuloo.prices;

import cc.xuloo.betfair.api.PricesService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.akka.AkkaGuiceSupport;

public class PricesModule extends AbstractModule implements ServiceGuiceSupport, AkkaGuiceSupport {

    private final static Logger log = LoggerFactory.getLogger(PricesModule.class);

    @Override
    protected void configure() {
        log.info("configuring prices module");

        bindService(PricesService.class, PricesServiceImpl.class);

        // Start the background monitoring of betfair market prices.
//        bind(BetfairMonitor.class).asEagerSingleton();
    }

//    @Provides
//    @Singleton
//    @Named("betfair-client")
//    ActorRef betfairClient(Config config, ActorSystem system, PersistentEntityRegistry registry) {
//        registry.register(BetfairEntity.class);
//
//        return Betfair.buildClient(BetfairConfiguration.builder()
//                .config(config)
//                .system(system)
//                .streamMessageHandler(system.actorOf(DefaultStreamMessageHandler.props(registry), "stream-message-handler"))
//                .build());
//    }
}
