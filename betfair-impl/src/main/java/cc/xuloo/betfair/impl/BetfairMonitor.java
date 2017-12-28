package cc.xuloo.betfair.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.client.BetfairConnection;
import cc.xuloo.betfair.client.ExchangeApi;
import cc.xuloo.betfair.client.actors.*;
import cc.xuloo.betfair.client.asynchttp.AsyncHttpBetfairConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class BetfairMonitor {

    private final static Logger log = LoggerFactory.getLogger(BetfairMonitor.class);

    @Inject
    public BetfairMonitor(Config config, ActorSystem system) {
        ObjectMapper mapper = new ObjectMapper();

        ActorRef socketListener = system.actorOf(SocketListeningActor.props(), "socket-listener");

        InetSocketAddress address = InetSocketAddress.createUnresolved(config.getString("betfair.stream.uri"), config.getInt("betfair.stream.port"));
        ActorRef socket = system.actorOf(SocketActor.props(address, socketListener), "socket-actor");

        ActorRef betfairSocket = system.actorOf(BetfairSocketActor.props(socket, mapper), "betfair-socket");

        BetfairConnection connection = new AsyncHttpBetfairConnection(config);
        ExchangeApi exchange = new ExchangeApi(connection);

        ActorRef betfairStream = system.actorOf(BetfairStreamActor.props(betfairSocket), "betfair-stream");
        ActorRef betfairExchange = system.actorOf(BetfairExchangeActor.props(exchange), "betfair-exchange");

        ActorRef betfairClient = system.actorOf(BetfairClientActor.props(config, betfairExchange, betfairStream), "betfair-client");
    }
}
