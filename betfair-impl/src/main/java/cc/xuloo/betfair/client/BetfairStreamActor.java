package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.stream.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BetfairStreamActor extends AbstractActor {

    public static Props props(ObjectMapper mapper, ActorRef socket) {
        return Props.create(BetfairStreamActor.class, () -> new BetfairStreamActor(mapper, socket));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ObjectMapper mapper;

    private final ActorRef socket;

    public BetfairStreamActor(ObjectMapper mapper, ActorRef socket) {
        this.mapper = mapper;
        this.socket = socket;
    }

    @Override
    public Receive createReceive() {
        return disconnected();
    }

    public Receive disconnected() {
        return receiveBuilder()
                .match(StreamCommand.Connect.class, cmd -> {
                    getContext().become(connecting(cmd.getSession(), getSender()));
                    socket.tell(ConnectMessage.builder()
                            .host(cmd.getHost())
                            .port(cmd.getPort())
                            .id(0)
                            .build(), getSelf());
                })
                .matchAny(o -> log.warning("i'm in state 'disconnected' and i don't know what to do with {}", o))
                .build();
    }

    public Receive connecting(BetfairSession session, ActorRef listener) {
        return receiveBuilder()
                .match(ConnectionMessage.class, msg -> {
                    log.info("connected to stream -> {}", msg);

                    getContext().become(authenticating(listener));

                    socket.tell(AuthenticationMessage.builder()
                            .id(1)
                            .appKey(session.applicationKey())
                            .session(session.sessionToken())
                            .build(), getSelf());
                })
                .matchAny(o -> log.warning("i'm in state 'connecting' and i don't know what to do with {}", o))
                .build();
    }

    public Receive authenticating(ActorRef listener) {
        return receiveBuilder()
                .match(StatusMessage.class, msg -> {

                    if (msg.getId() != null && msg.getId().equals(1)) {
                        log.info("stream succesfully authenticated");

                        getContext().become(authorised());
                        listener.tell(new StreamEvent.Connected(), getSelf());
                    } else {
                        log.info("in state 'authenticatingStream but received a StatusMessage with id {} -> {}", msg.getId(), msg);
                    }
                })
                .matchAny(o -> log.warning("i'm in state 'authenticating' and i don't know what to do with {}", o))
                .build();
    }

    public Receive authorised() {
        return receiveBuilder()
                .match(RequestMessage.class, msg -> {
                    socket.forward(msg, getContext());
                })
                .matchAny(o -> log.warning("i'm in state 'authorised' and i don't know what to do with {}", o))
                .build();
    }
}
