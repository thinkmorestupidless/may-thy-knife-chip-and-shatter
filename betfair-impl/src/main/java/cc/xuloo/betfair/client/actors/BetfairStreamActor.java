package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class BetfairStreamActor extends AbstractActor {

    public static Props props() {
        return Props.create(BetfairStreamActor.class, BetfairStreamActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
