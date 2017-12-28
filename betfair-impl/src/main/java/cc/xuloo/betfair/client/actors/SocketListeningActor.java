package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.util.ByteString;

public class SocketListeningActor extends AbstractActor {

    public static Props props() {
        return Props.create(SocketListeningActor.class, SocketListeningActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ByteString.class, this::handleMessage)
                .build();
    }

    public void handleMessage(ByteString bytes) {

    }
}
