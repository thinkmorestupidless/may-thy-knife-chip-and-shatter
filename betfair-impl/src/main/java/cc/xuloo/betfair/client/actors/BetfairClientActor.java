package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.BetfairSession;
import cc.xuloo.betfair.client.LoginResponse;
import cc.xuloo.betfair.stream.AuthenticationMessage;
import cc.xuloo.betfair.stream.StreamMessage;
import com.typesafe.config.Config;

public class BetfairClientActor extends AbstractActorWithStash {

    public static Props props(Config config, ActorRef exchange, ActorRef stream) {
        return Props.create(BetfairClientActor.class, () -> new BetfairClientActor(config, exchange, stream));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final Config config;

    private final ActorRef exchange;

    private final ActorRef stream;

    private final Receive loggedOut = receiveBuilder()
                                        .match(ExchangeProtocol.Login.class, this::handleLogin)
                                        .match(LoginResponse.class, this::handleLoginResponse)
                                        .match(ExchangeProtocol.class, x -> stash())
                                        .matchAny(o -> log.info("i don't know what to do with {}", o))
                                        .build();

    private final Receive loggedIn = receiveBuilder()
                                        .match(StreamMessage.class, this::handleStreamMessage)
                                        .build();

    private BetfairSession session = BetfairSession.loggedOut();

    public BetfairClientActor(Config config, ActorRef exchange, ActorRef stream) {
        this.config = config;
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

            stream.tell(AuthenticationMessage.builder().id(0).appKey(session.applicationKey()).session(session.sessionToken()).build(), getSelf());

            unstashAll();
            getContext().become(loggedIn);
        } else {
            log.warning("failed to login successfully");
        }
    }

    public void handleStreamMessage(StreamMessage msg) {
        stream.forward(msg, getContext());
    }
}
