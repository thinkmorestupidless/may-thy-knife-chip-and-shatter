package cc.xuloo.prices;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.Betfair;
import cc.xuloo.betfair.BetfairConfiguration;
import cc.xuloo.betfair.api.PricesService;
import cc.xuloo.prices.betfair.BetfairMonitor;
import cc.xuloo.prices.betfair.MarketChangeMessageStreamHandler;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.akka.AkkaGuiceSupport;

import javax.inject.Named;

public class PricesModule extends AbstractModule implements ServiceGuiceSupport, AkkaGuiceSupport {

    private final static Logger log = LoggerFactory.getLogger(PricesModule.class);

    @Override
    protected void configure() {
        log.info("configuring prices module");

        bindService(PricesService.class, PricesServiceImpl.class);

        // Start the background monitoring of betfair market prices.
        bind(BetfairMonitor.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    @Named("betfair-client")
    ActorRef betfairClient(Config config, ActorSystem system, PersistentEntityRegistry registry) {
        registry.register(PricesEntity.class);

        return Betfair.buildClient(BetfairConfiguration.builder()
                .config(config)
                .system(system)
                .streamMessageHandler(system.actorOf(MarketChangeMessageStreamHandler.props(registry), "market-change-message-handler"))
                .build());
    }
}
