package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.ByteString;
import cc.xuloo.betfair.client.BetfairSession;
import cc.xuloo.betfair.client.LoginResponse;
import cc.xuloo.betfair.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opengamma.strata.collect.Unchecked;
import com.typesafe.config.Config;

public class BetfairClientActor extends AbstractActorWithStash {

    public static Props props(Config config, ObjectMapper mapper, ActorRef exchange, ActorRef stream) {
        return Props.create(BetfairClientActor.class, config, mapper, exchange, stream);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final Config config;

    private final ObjectMapper mapper;

    private final ActorRef exchange;

    private final ActorRef stream;

    private final Receive loggedOut = receiveBuilder()
                                        .match(ExchangeProtocol.Login.class, this::handleLogin)
                                        .match(LoginResponse.class, this::handleLoginResponse)
                                        .match(ByteString.class, this::handleByteString)
                                        .match(ExchangeProtocol.class, x -> stash())
                                        .matchAny(o -> log.info("i don't know what to do with {}", o))
                                        .build();

    private final Receive loggedIn = receiveBuilder()
                                        .match(ExchangeProtocol.class, this::handleExchangeMessage)
                                        .match(StreamMessage.class, this::handleStreamMessage)
                                        .match(ByteString.class, this::handleByteString)
                                        .matchAny(o -> log.info("i'm logged in and i don't know what to do with {}", o))
                                        .build();

    private BetfairSession session = BetfairSession.loggedOut();

    public BetfairClientActor(Config config, ObjectMapper mapper, ActorRef exchange, ActorRef stream) {
        this.config = config;
        this.mapper = mapper;
        this.exchange = exchange;
        this.stream = stream;
    }

    @Override
    public void preStart() throws Exception {
        log.info("starting up the betfair client");

        if (config.getString("mtkcas.betfair.monitor").equals("auto-start")) {
            log.info("auto-starting betfair client - logging in");

            exchange.tell(new ExchangeProtocol.Login(), getSelf());
        }
    }

    @Override
    public void postStop() {
        session = BetfairSession.loggedOut();
    }

    @Override
    public Receive createReceive() {
        return loggedOut;
    }

    public void handleLogin(ExchangeProtocol.Login msg) {
        exchange.tell(msg, getSelf());
    }

    public void handleLoginResponse(LoginResponse msg) {
        if (msg.getLoginStatus().equals("SUCCESS")) {
            log.info("logged in to Betfair successfully");

            session = BetfairSession.loggedIn(msg.getSessionToken(), config.getString("betfair.applicationKey"));

            stream.tell(ConnectMessage.builder()
                    .host(config.getString("betfair.stream.uri"))
                    .port(config.getInt("betfair.stream.port"))
                    .id(0)
                    .build(), getSelf());
        } else {
            log.warning("failed to login successfully");
        }
    }

    public void handleByteString(ByteString bytes) {
        ResponseMessage msg = Unchecked.wrap(() -> mapper.readValue(bytes.utf8String(), ResponseMessage.class));

        if (msg instanceof ConnectionMessage) {
            log.info("handling connection message");

            stream.tell(AuthenticationMessage.builder()
                    .id(1)
                    .appKey(session.applicationKey())
                    .session(session.sessionToken())
                    .build(), getSelf());

        } else if (msg instanceof StatusMessage) {
            log.info("Handling status message {}", msg.getId());

            if (msg.getId().equals(1)) {
                log.info("stream succesfully authenticated");

                unstashAll();
                getContext().become(loggedIn);
            }
        }
    }

    public void handleStreamMessage(StreamMessage msg) {
        stream.forward(msg, getContext());
    }

    public void handleExchangeMessage(ExchangeProtocol msg) {
        log.info("handling exchange message");

        ExchangeProtocol ep = ExchangeProtocol.Command.builder()
                .command(msg)
                .session(session)
                .build();

        exchange.forward(ep, getContext());
    }
}
