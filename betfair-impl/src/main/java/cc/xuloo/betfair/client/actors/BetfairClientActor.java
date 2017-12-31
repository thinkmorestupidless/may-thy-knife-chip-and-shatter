package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.ByteString;
import cc.xuloo.betfair.client.BetfairSession;
import cc.xuloo.betfair.client.LoginResponse;
import cc.xuloo.betfair.impl.BetfairCommand;
import cc.xuloo.betfair.impl.BetfairEntity;
import cc.xuloo.betfair.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.typesafe.config.Config;

import java.io.IOException;
import java.util.List;

public class BetfairClientActor extends AbstractActorWithStash {

    public static Props props(Config config, ObjectMapper mapper, ActorRef exchange, ActorRef stream, ActorRef listener, PersistentEntityRegistry registry) {
        return Props.create(BetfairClientActor.class, config, mapper, exchange, stream, listener, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final Config config;

    private final ObjectMapper mapper;

    private final ActorRef exchange;

    private final ActorRef stream;

    private final ActorRef listener;

    private final PersistentEntityRegistry registry;

    private BetfairSession session = BetfairSession.loggedOut();

    private StringBuilder buffer = new StringBuilder();

    public BetfairClientActor(Config config, ObjectMapper mapper, ActorRef exchange, ActorRef stream, ActorRef listener, PersistentEntityRegistry registry) {
        this.config = config;
        this.mapper = mapper;
        this.exchange = exchange;
        this.stream = stream;
        this.listener = listener;
        this.registry = registry;
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
        buffer = new StringBuilder();
    }

    @Override
    public Receive createReceive() {
        return loggedOut();
    }

    private Receive loggedOut() {
        return receiveBuilder()
                .match(ExchangeProtocol.Login.class, msg -> {
                    exchange.tell(msg, getSelf());
                })
                .match(LoginResponse.class, msg -> {
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
                })
                .match(ByteString.class, this::handleByteString)
                .match(ExchangeProtocol.class, x -> stash())
                .matchAny(o -> log.info("i don't know what to do with {}", o))
                .build();
    }

    private Receive loggedIn() {
        return receiveBuilder()
                .match(ExchangeProtocol.class, msg -> {
                    exchange.tell(ExchangeProtocol.Command.builder()
                            .command(msg)
                            .session(session)
                            .listener(getSender())
                            .build(), getSelf());
                })
                .match(StreamProtocol.class, msg -> {
                    stream.forward(msg, getContext());
                })
                .match(ByteString.class, this::handleByteString)
                .matchAny(o -> log.info("i'm logged in and i don't know what to do with {}", o))
                .build();

    }

    private void buffer(ByteString bytes) {

    }

    public void handleByteString(ByteString bytes) {

        String s = bytes.utf8String();
        buffer.append(s);

        if (buffer.toString().endsWith("\r\n")) {

            ResponseMessage msg = null;

            try {
                msg = mapper.readValue(buffer.toString(), ResponseMessage.class);
            } catch (IOException e) {
                log.error("problem reading json value -> {} -> {}", buffer.toString(), e);
            }

            buffer = new StringBuilder();

            if (msg != null) {
                if (msg instanceof ConnectionMessage) {
                    log.info("handling connection message");

                    stream.tell(AuthenticationMessage.builder()
                            .id(1)
                            .appKey(session.applicationKey())
                            .session(session.sessionToken())
                            .build(), getSelf());

                } else if (msg instanceof StatusMessage) {
                    log.info("Handling status message {}", msg);

                    if (msg.getId() != null && msg.getId().equals(1)) {
                        log.info("stream succesfully authenticated");

                        unstashAll();
                        getContext().become(loggedIn());
                    }
                } else if (msg instanceof MarketChangeMessage) {
                    log.info("handling market change message -> {}", msg);

                    MarketChangeMessage mcm = (MarketChangeMessage) msg;

                    if (mcm.getCt() == MarketChangeMessage.CtEnum.HEARTBEAT) {
                        stream.tell(new StreamProtocol.HeartbeatReceived(), getSelf());
                    } else if (mcm.getCt() == MarketChangeMessage.CtEnum.SUB_IMAGE) {

                        if (mcm.getMc() != null) {
                            List<MarketChange> marketChanges = mcm.getMc();

                            marketChanges.forEach(marketChange -> {
                                PersistentEntityRef<BetfairCommand> entity = registry.refFor(BetfairEntity.class, marketChange.getMarketDefinition().getEventId());
//                            entity.ask(new BetfairCommand.AddMarketCatalogue())

                                if (marketChange.getImg() != null) {
                                    log.info("Handling Image for {}", marketChange.getId());
                                } else {
                                    log.info("handling update for {}", marketChange.getId());
                                }
                            });
                        }
                    }
                } else {
                    log.info("i don't know what to do with -> {}", msg);
                }
            }
        }
    }
}
