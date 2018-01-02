package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.api.BetfairService;
import cc.xuloo.betfair.client.BetfairConnection;
import cc.xuloo.betfair.client.ExchangeApi;
import cc.xuloo.betfair.client.actors.*;
import cc.xuloo.betfair.client.asynchttp.AsyncHttpBetfairConnection;
import cc.xuloo.betfair.client.settings.BetfairSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
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

import java.net.InetSocketAddress;

public class BetfairModule extends AbstractModule implements ServiceGuiceSupport, AkkaGuiceSupport {

    private final static Logger log = LoggerFactory.getLogger(BetfairModule.class);

    @Override
    protected void configure() {
        log.info("configuring betfair module");

        bindService(BetfairService.class, BetfairServiceImpl.class);

        bind(BetfairMonitor.class).asEagerSingleton();
    }

    @Provides
    @Singleton
    @Named("betfair-client")
    ActorRef betfairClient(Config config, ActorSystem system, PersistentEntityRegistry registry) {
        registry.register(BetfairEntity.class);

        ObjectMapper mapper = new ObjectMapper().registerModule(new JodaModule());

        ActorRef streamHandler = system.actorOf(DefaultStreamMessageHandler.props(registry), "stream-handler");

        InetSocketAddress address = InetSocketAddress.createUnresolved(config.getString("betfair.stream.uri"), config.getInt("betfair.stream.port"));
        ActorRef socket = system.actorOf(SocketActor.props(), "socket-actor");

        ActorRef betfairSocket = system.actorOf(BetfairSocketActor.props(socket, mapper, streamHandler), "betfair-socket");

        BetfairSettings settings = BetfairSettings.fromConfig(config);

        BetfairConnection connection = new AsyncHttpBetfairConnection(settings.getClient().getCredentials());
        ExchangeApi exchange = new ExchangeApi(connection);

        ActorRef betfairStream = system.actorOf(BetfairStreamActor.props(mapper, betfairSocket), "betfair-stream");
        ActorRef betfairExchange = system.actorOf(BetfairExchangeActor.props(exchange), "betfair-exchange");

        return system.actorOf(BetfairClientActor.props(mapper, settings, betfairExchange, betfairStream, streamHandler), "betfair-client");
    }
}
