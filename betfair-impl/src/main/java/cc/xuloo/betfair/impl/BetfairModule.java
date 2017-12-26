package cc.xuloo.betfair.impl;

import cc.xuloo.betfair.api.BetfairService;
import cc.xuloo.betfair.client.BetfairClient;
import cc.xuloo.betfair.client.asynchttp.AsyncHttpBetfairConnection;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
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

        bind(BetfairMonitor.class).asEagerSingleton();

        bindActor(BetfairWorker.class, "betfair-worker");
        bindActorFactory(EventMonitor.class, BetfairProtocol.Factory.class);
    }

    @Provides
    @Singleton
    public BetfairClient config(Config conf) {
        return new BetfairClient(new AsyncHttpBetfairConnection(conf));
    }
}
