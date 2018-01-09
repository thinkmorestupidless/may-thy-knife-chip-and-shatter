package cc.xuloo.betfair;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import cc.xuloo.betfair.client.*;
import cc.xuloo.betfair.client.exchange.ExchangeApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.net.InetSocketAddress;

public class Betfair {

    public static ActorRef buildClient(BetfairConfiguration config) {
        ObjectMapper mapper = mapper(config.getMapper());

        return config.getSystem()
                .actorOf(BetfairClientActor.props(mapper,
                        config.getSettings(),
                        exchangeActor(config),
                        streamActor(config, mapper)),
                        "betfair-client");
    }

    public static ObjectMapper mapper(ObjectMapper m) {
        if (m == null) {
            return new ObjectMapper().registerModule(new JodaModule());
        }

        return m;
    }

    public static ActorRef streamActor(BetfairConfiguration config, ObjectMapper mapper) {
        if (config.getConfig().hasPath("betfair.stream")) {
            ActorSystem system = config.getSystem();

            InetSocketAddress address = InetSocketAddress.createUnresolved(config.getSettings().getStream().getUri(), config.getSettings().getStream().getPort());
            ActorRef socket = system.actorOf(SocketActor.props(), "socket-actor");
            ActorRef bfSocket = system.actorOf(BetfairSocketActor.props(socket, mapper, streamMessageHandler(config)), "betfair-socket");

            return system.actorOf(BetfairStreamActor.props(mapper, bfSocket), "betfair-stream");
        }

        return config.getSystem().actorOf(EmptyStreamActor.props(), "empty-betfair-stream");
    }

    public static ActorRef streamMessageHandler(BetfairConfiguration config) {
        if (config.getStreamMessageHandler() == null) {
            return config.getSystem().actorOf(EmptyStreamMessageHandler.props(), "empty-stream-message-handler");
        }

        return config.getStreamMessageHandler();
    }

    public static ActorRef exchangeActor(BetfairConfiguration config) {
        return config.getSystem().actorOf(BetfairExchangeActor.props(exchange(config)), "betfair-exchange");
    }

    public static ExchangeApi exchange(BetfairConfiguration config) {
        return new ExchangeApi(connection(config));
    }

    public static BetfairConnection connection(BetfairConfiguration config) {
        if (config.getConnection() == null) {
            return new AsyncHttpBetfairConnection(config.getSettings().getClient().getCredentials());
        }

        return config.getConnection();
    }
}
