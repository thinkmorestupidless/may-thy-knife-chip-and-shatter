package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.Betfair;
import cc.xuloo.betfair.BetfairConfiguration;
import cc.xuloo.betfair.api.BetfairService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.libs.akka.AkkaGuiceSupport;

public class BetfairModule extends AbstractModule implements ServiceGuiceSupport, AkkaGuiceSupport {

    private final static Logger log = LoggerFactory.getLogger(BetfairModule.class);

    @Override
    protected void configure() {
        log.info("configuring betfair module");

        bindService(BetfairService.class, BetfairServiceImpl.class);

        // Start the background monitoring of betfair market prices.
        bind(BetfairMonitor.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    @Named("betfair-client")
    ActorRef betfairClient(Config config, ActorSystem system, PersistentEntityRegistry registry) {
        registry.register(BetfairEntity.class);

        return Betfair.buildClient(BetfairConfiguration.builder()
                .config(config)
                .system(system)
                .streamMessageHandler(system.actorOf(DefaultStreamMessageHandler.props(registry), "stream-message-handler"))
                .build());
    }
}
