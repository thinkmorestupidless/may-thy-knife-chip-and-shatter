package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Source;
import cc.xuloo.betfair.stream.MarketChange;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

public class SocketListeningActor extends AbstractActor {

    public static Props props(PersistentEntityRegistry registry) {
        return Props.create(SocketListeningActor.class, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final PersistentEntityRegistry registry;

    public SocketListeningActor(PersistentEntityRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MarketChange.class, this::handleMarketChange)
                .build();
    }

    public void handleMarketChange(MarketChange msg) {
        log.info("received -> {}", msg);
    }
}
