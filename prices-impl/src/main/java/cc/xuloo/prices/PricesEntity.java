package cc.xuloo.prices;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class PricesEntity extends PersistentEntity<PricesCommand, PricesEvent, PricesState> {

    @Override
    public Behavior initialBehavior(Optional<PricesState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(PricesState.empty()));

        b.setCommandHandler(PricesCommand.AddFixture.class, (cmd, ctx) ->
            ctx.thenPersist(new PricesEvent.FixtureAdded(cmd.getEvent()),
                    evt -> ctx.reply(Done.getInstance())));

        b.setEventHandlerChangingBehavior(PricesEvent.FixtureAdded.class,
                evt -> fixtureAvailable(state().withEvent(evt.getEvent())));

        return b.build();
    }

    public Behavior fixtureAvailable(PricesState state) {
        BehaviorBuilder b = newBehaviorBuilder(state);

        b.setCommandHandler(PricesCommand.AddMarketCatalogue.class, (cmd, ctx) ->
                ctx.thenPersist(new PricesEvent.MarketCatalogueAdded(cmd.getCatalogue()),
                        evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(PricesEvent.MarketCatalogueAdded.class,
                evt -> state().withMarketCatalogue(evt.getCatalogue()));

        b.setCommandHandler(PricesCommand.AddMarketData.class, (cmd, ctx) ->
                ctx.thenPersist(new PricesEvent.MarketDataAdded(cmd.getData()),
                        evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(PricesEvent.MarketDataAdded.class,
                evt -> state().withMarketData(evt.getData()));

        b.setCommandHandler(PricesCommand.MergeMarketData.class, (cmd, ctx) ->
                ctx.thenPersist(new PricesEvent.MarketDataMerged(cmd.getData()),
                        evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(PricesEvent.MarketDataMerged.class,
                evt -> state().withMergedMarketData(evt.getData()));

        return b.build();
    }
}
