package cc.xuloo.betfair.client.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.impl.BetfairCommand;
import cc.xuloo.betfair.impl.BetfairEntity;
import cc.xuloo.betfair.stream.MarketChange;
import cc.xuloo.betfair.stream.MarketChangeMessage;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import java.util.List;

public class DefaultStreamMessageHandler extends AbstractActor {

    public static Props props(PersistentEntityRegistry registry) {
        return Props.create(DefaultStreamMessageHandler.class, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final PersistentEntityRegistry registry;

    public DefaultStreamMessageHandler(PersistentEntityRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(MarketChangeMessage.class, msg -> {
                    log.info("handling market change message -> {}", msg);

                    if (msg.getMc() != null) {
                        List<MarketChange> marketChanges = msg.getMc();

                        marketChanges.forEach(marketChange -> {
                            PersistentEntityRef<BetfairCommand> entity = registry.refFor(BetfairEntity.class, marketChange.getMarketDefinition().getEventId());

                            if (marketChange.getImg() != null && marketChange.getImg()) {
                                log.info("Adding market data for {}", marketChange.getId());

                                entity.ask(new BetfairCommand.AddMarketData(marketChange));
                            } else {
                                log.info("Merging market data for {}", marketChange.getId());
                            }
                        });
                    }
                })
                .matchAny(o -> log.info("I don't know what to do with {}", o))
                .build();
    }
}
