package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.api.BetfairService;
import cc.xuloo.betfair.client.BetfairConnection;
import cc.xuloo.betfair.client.ExchangeApi;
import cc.xuloo.betfair.client.actors.*;
import cc.xuloo.betfair.client.asynchttp.AsyncHttpBetfairConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
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

        bindActor(BetfairWorker.class, "betfair-worker");
        bindActorFactory(EventMonitor.class, BetfairProtocol.Factory.class);
    }

    @Provides
    @Singleton
    @Named("betfair-client")
    ActorRef betfairClient(Config config, ActorSystem system) {
        ObjectMapper mapper = new ObjectMapper();

        ActorRef socketListener = system.actorOf(SocketListeningActor.props(), "socket-listener");

        InetSocketAddress address = InetSocketAddress.createUnresolved(config.getString("betfair.stream.uri"), config.getInt("betfair.stream.port"));
        ActorRef socket = system.actorOf(SocketActor.props(address, socketListener), "socket-actor");

        ActorRef betfairSocket = system.actorOf(BetfairSocketActor.props(socket, mapper), "betfair-socket");

        BetfairConnection connection = new AsyncHttpBetfairConnection(config);
        ExchangeApi exchange = new ExchangeApi(connection);

        ActorRef betfairStream = system.actorOf(BetfairStreamActor.props(betfairSocket), "betfair-stream");
        ActorRef betfairExchange = system.actorOf(BetfairExchangeActor.props(exchange), "betfair-exchange");

        return system.actorOf(BetfairClientActor.props(config, betfairExchange, betfairStream), "betfair-client");
    }
}
