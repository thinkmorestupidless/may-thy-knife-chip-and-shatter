package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class EmptyStreamMessageHandler extends AbstractActor {

    public static Props props() {
        return Props.create(EmptyStreamMessageHandler.class, EmptyStreamMessageHandler::new);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(o -> log.warning("i don't know what to do with {}", o))
                .build();
    }
}
