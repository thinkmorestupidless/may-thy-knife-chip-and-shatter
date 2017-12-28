package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.stream.RequestMessage;

public class SocketWrapper extends AbstractActor {

    public static class Init {}
    public static class Ack {
        private static Ack instance;
        public static Ack instance() {
            return instance = instance == null ? new Ack() : instance;
        }
    }
    public static class Complete {}

    LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props(ActorRef socket) {
        return Props.create(SocketWrapper.class, () -> new SocketWrapper(socket));
    }

    private final ActorRef socket;

    public SocketWrapper(ActorRef socket) {
        this.socket = socket;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Init.class, init -> getSender().tell(Ack.instance(), getSelf()))
                .match(RequestMessage.class, msg -> {
                    socket.tell(msg, getSelf());
                    getSender().tell(Ack.instance(), getSelf());
                })
                .match(Complete.class, msg -> log.info("socket stream complete"))
                .build();
    }
}
