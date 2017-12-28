package cc.xuloo.betfair.client;

import akka.actor.AbstractActor;

public class BetfairExchangeActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
