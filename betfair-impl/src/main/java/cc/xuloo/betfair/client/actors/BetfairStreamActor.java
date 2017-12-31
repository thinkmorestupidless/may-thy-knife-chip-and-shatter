package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.stream.StreamProtocol;

public class BetfairStreamActor extends AbstractActor {

    public static Props props(ActorRef socket) {
        return Props.create(BetfairStreamActor.class, () -> new BetfairStreamActor(socket));
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final ActorRef socket;

    public BetfairStreamActor(ActorRef socket) {
        this.socket = socket;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamProtocol.class, msg -> socket.forward(msg, getContext()))
                .matchAny(o -> log.warning("i don't know what to do with {}", o))
                .build();
    }
}
