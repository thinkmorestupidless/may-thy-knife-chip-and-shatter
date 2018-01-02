package cc.xuloo.betfair.client;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.settings.BetfairSettings;
import cc.xuloo.betfair.client.stream.StreamProtocol;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BetfairClientActor extends AbstractActorWithStash {

    public static Props props(ObjectMapper mapper, BetfairSettings settings, ActorRef exchange, ActorRef stream, ActorRef listener) {
        return Props.create(BetfairClientActor.class, () -> new BetfairClientActor(mapper, settings, exchange, stream));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ObjectMapper mapper;

    private final BetfairSettings settings;

    private final ActorRef exchange;

    private final ActorRef stream;

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
                .matchAny(o -> log.warning("in state 'disconnected' - unable to handle {}", o))
                .build();
    }

    private Receive connectingExchange() {
        return receiveBuilder()
                .match(ExchangeEvent.Connected.class, evt -> {
                    log.info("exchange connected - connecting stream");

                    getContext().become(exchangeConnected(evt.getSession()));
                    getSelf().tell(new StreamCommand.Connect(evt.getSession(), settings.getStream().getUri(), settings.getStream().getPort()), getSelf());
                })
                .match(ExchangeProtocol.class, cmd -> {
                    stash();
                })
                .matchAny(o -> log.warning("in state 'connectingExchange' - unable to handle {}", o))
                .build();
    }

    private Receive exchangeConnected(BetfairSession session) {
        return receiveBuilder()
                .match(StreamCommand.Connect.class, cmd -> {
                    getContext().become(connectingStream(session));
                    stream.tell(new StreamCommand.Connect(session, settings.getStream().getUri(), settings.getStream().getPort()), getSelf());
                })
                .matchAny(o -> log.warning("in state 'exchangeConnected' - unable to handle {}", o))
                .build();
    }

    private Receive connectingStream(BetfairSession session) {
        return receiveBuilder()
                .match(StreamEvent.Connected.class, evt -> {
                    log.info("stream connected");

                    getContext().become(connected(session));
                    unstashAll();
                })
                .match(ExchangeProtocol.class, cmd -> {
                    stash();
                })
                .matchAny(o -> log.warning("in state 'connectingStream' - unable to handle {}", o))
                .build();
    }

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
                .matchAny(o -> log.warning("i'm logged in and i don't know what to do with {}", o))
                .build();
    }
}
