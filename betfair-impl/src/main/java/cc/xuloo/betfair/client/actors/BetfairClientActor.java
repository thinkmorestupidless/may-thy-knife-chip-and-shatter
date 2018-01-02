package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.BetfairSession;
import cc.xuloo.betfair.client.settings.BetfairSettings;
import cc.xuloo.betfair.stream.StreamProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BetfairClientActor extends AbstractActorWithStash {

    public static Props props(ObjectMapper mapper, BetfairSettings settings, ActorRef exchange, ActorRef stream, ActorRef listener) {
        return Props.create(BetfairClientActor.class, () -> new BetfairClientActor(mapper, settings, exchange, stream));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

//    private final Config config;

    private final ObjectMapper mapper;

    private final BetfairSettings settings;

//    private final BetfairCredentials credentials;

    private final ActorRef exchange;

    private final ActorRef stream;

//    private final ActorRef streamHandler;

//    private BetfairSession session = BetfairSession.loggedOut();

    private StringBuilder buffer = new StringBuilder();

    public BetfairClientActor(ObjectMapper mapper, BetfairSettings settings, ActorRef exchange, ActorRef stream) {
        this.mapper = mapper;
        this.settings = settings;
        this.exchange = exchange;
        this.stream = stream;
    }

    @Override
    public void preStart() throws Exception {
        log.info("starting up the betfair client");

        if (settings.getClient().autoStart()) {
            log.info("auto-starting betfair client - logging in");

            getSelf().tell(new BetfairCommand.Connect(), getSelf());
        }
    }

    @Override
    public void postStop() {
//        session = BetfairSession.loggedOut();
        buffer = new StringBuilder();
    }

    @Override
    public Receive createReceive() {
        return disconnected();
    }

    private Receive disconnected() {
        return receiveBuilder()
                .match(BetfairCommand.Connect.class, cmd -> {
                    log.info("connecting exchange");

                    getContext().become(connectingExchange());
                    exchange.tell(new ExchangeCommand.Connect(getSelf(), settings.getClient().getCredentials().getApplicationKey()), getSelf());
                })
                .match(ExchangeProtocol.class, cmd -> {
                    stash();
                })
                .matchAny(o -> log.info("in state 'disconnected' - unable to handle {}", o))
                .build();
    }

    private Receive connectingExchange() {
        return receiveBuilder()
                .match(ExchangeEvent.Connected.class, evt -> {
                    log.info("exchange connected - connecting stream");

                    getContext().become(exchangeConnected(evt.getSession()));
                    getSelf().tell(new StreamCommand.Connect(evt.getSession(), settings.getStream().getUri(), settings.getStream().getPort()), getSelf());
                })
//                .match(LoginResponse.class, msg -> {
//                    if (msg.getLoginStatus().equals("SUCCESS")) {
//                        log.info("logged in to Betfair successfully");
//
//                        session = BetfairSession.loggedIn(msg.getSessionToken(), config.getString("betfair.applicationKey"));
//
//                        getContext().become(connectingStream());
//
//                        getSelf().tell(new BetfairClientProtocol.ConnectStream(), getSelf());
//                    } else {
//                        log.warning("failed to login successfully -> {}", msg.toString());
//                        getContext().become(loggedOut());
//                    }
//                })
                .match(ExchangeProtocol.class, cmd -> {
                    stash();
                })
                .matchAny(o -> log.info("in state 'connectingExchange' - unable to handle {}", o))
                .build();
    }

    private Receive exchangeConnected(BetfairSession session) {
        return receiveBuilder()
                .match(StreamCommand.Connect.class, cmd -> {
                    getContext().become(connectingStream(session));
                    stream.tell(new StreamCommand.Connect(session, settings.getStream().getUri(), settings.getStream().getPort()), getSelf());
                })
                .matchAny(o -> log.info("in state 'exchangeConnected' - unable to handle {}", o))
                .build();
    }

    private Receive connectingStream(BetfairSession session) {
        return receiveBuilder()
                .match(StreamEvent.Connected.class, evt -> {
                    log.info("stream connected");

                    getContext().become(connected(session));
                    unstashAll();
                })
//                .match(BetfairClientProtocol.ConnectStream.class, cmd -> {
//                    getContext().become(connectingStream());
//
//                    stream.tell(ConnectMessage.builder()
//                                .host(config.getString("betfair.stream.uri"))
//                                .port(config.getInt("betfair.stream.port"))
//                                .id(0)
//                                .build(), getSelf());
//                })
//                .match(ByteString.class, msg -> {
//                    ConnectionMessage cm = mapper.readValue(msg.utf8String(), ConnectionMessage.class);
//
//                    log.info("connected to stream -> {}", cm);
//
//                    getContext().become(authenticatingStream());
//
//                    stream.tell(AuthenticationMessage.builder()
//                            .id(1)
//                            .appKey(session.applicationKey())
//                            .session(session.sessionToken())
//                            .build(), getSelf());
//                })
                .match(ExchangeProtocol.class, cmd -> {
                    stash();
                })
                .matchAny(o -> log.info("in state 'connectingStream' - unable to handle {}", o))
                .build();
    }

//    private Receive authenticatingStream() {
//        return receiveBuilder()
//                .match(ByteString.class, msg -> {
//                    StatusMessage sm = mapper.readValue(msg.utf8String(), StatusMessage.class);
//
//                    if (sm.getId() != null && sm.getId().equals(1)) {
//                        log.info("stream succesfully authenticated");
//
//                        getContext().become(loggedIn());
//                        unstashAll();
//                    } else {
//                        log.info("in state 'authenticatingStream but received a StatusMessage with id {} -> {}", sm.getId(), msg);
//                    }
//                })
//                .match(ExchangeProtocol.class, cmd -> {
//                    stash();
//                })
//                .matchAny(o -> log.info("in state 'authenticatingStream' - unable to handle {}", o))
//                .build();
//    }

    private Receive connected(BetfairSession session) {
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
//                .match(ByteString.class, this::handleByteString)
                .matchAny(o -> log.warning("i'm logged in and i don't know what to do with {}", o))
                .build();
    }

//    public void handleByteString(ByteString bytes) {
//
//        String s = bytes.utf8String();
//        log.info(s);
//        buffer.append(s);
//
//        if (buffer.toString().endsWith("\r\n")) {
//
//            log.info("parsing {}", buffer.toString());
//
//            ResponseMessage msg = null;
//
//            try {
//                msg = mapper.readValue(buffer.toString(), ResponseMessage.class);
//            } catch (IOException e) {
//                log.error("problem reading json value -> {} -> {}", buffer.toString(), e);
//            }
//
//            buffer = new StringBuilder();
//
//            if (msg != null) {
//                /*if (msg instanceof StatusMessage) {
//                    log.info("Handling status message {}", msg);
//
//                    if (msg.getId() != null) {
//                        if (msg.getId().equals(1)) {
//                            log.info("stream succesfully authenticated");
//
//                            unstashAll();
//                            getContext().become(loggedIn());
//                        } else if (msg.getId().equals(2)) {
//                            stream.tell(new StreamProtocol.HeartbeatReceived(), getSelf());
//                        }
//                    }
//                } else */if (msg instanceof MarketChangeMessage) {
//                    log.info("handling market change message -> {}", msg);
//
//                    MarketChangeMessage mcm = (MarketChangeMessage) msg;
//
//                    if (mcm.getCt() == MarketChangeMessage.CtEnum.HEARTBEAT) {
//                        stream.tell(new StreamProtocol.HeartbeatReceived(), getSelf());
//                    } else if (mcm.getCt() == MarketChangeMessage.CtEnum.SUB_IMAGE) {
//                        streamHandler.tell(mcm, getSelf());
//                    }
//                } else {
//                    log.debug("ignoring message -> {}", msg);
//                }
//            }
//        }
//    }
}
