package cc.xuloo.prices.betfair;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import cc.xuloo.betfair.client.stream.MarketChange;
import cc.xuloo.betfair.client.stream.MarketChangeMessage;
import cc.xuloo.prices.PricesCommand;
import cc.xuloo.prices.PricesEntity;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import java.util.List;

public class MarketChangeMessageStreamHandler extends AbstractActor {

    public static Props props(PersistentEntityRegistry registry) {
        return Props.create(MarketChangeMessageStreamHandler.class, registry);
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final PersistentEntityRegistry registry;

    public MarketChangeMessageStreamHandler(PersistentEntityRegistry registry) {
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
                            PersistentEntityRef<PricesCommand> entity = registry.refFor(PricesEntity.class, marketChange.getMarketDefinition().getEventId());

                            if (marketChange.getImg() != null && marketChange.getImg()) {
                                log.info("Adding market data for {}", marketChange.getId());

                                entity.ask(new PricesCommand.AddMarketData(marketChange))
                                .thenAccept(done -> log.info("market data added"));
                            } else {
                                log.info("Merging market data for {}", marketChange.getId());

                                entity.ask(new PricesCommand.MergeMarketData(marketChange))
                                .thenAccept(done -> log.info("market data merged"));
                            }
                        });
                    }
                })
                .matchAny(o -> log.info("I don't know what to do with {}", o))
                .build();
    }
}
