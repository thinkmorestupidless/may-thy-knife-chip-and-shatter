package cc.xuloo.betfair.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

public class BetfairEntity extends PersistentEntity<BetfairCommand, BetfairEvent, BetfairState> {

    @Override
    public Behavior initialBehavior(Optional<BetfairState> snapshotState) {
        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(BetfairState.empty()));

        b.setCommandHandler(BetfairCommand.AddFixture.class, (cmd, ctx) ->
            ctx.thenPersist(new BetfairEvent.FixtureAdded(cmd.getEvent()),
                    evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(BetfairEvent.FixtureAdded.class,
                evt -> state().withEvent(evt.getEvent()));

        b.setCommandHandler(BetfairCommand.AddMarketCatalogue.class, (cmd, ctx) ->
            ctx.thenPersist(new BetfairEvent.MarketCatalogueAdded(cmd.getCatalogue()),
                    evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(BetfairEvent.MarketCatalogueAdded.class,
                evt -> state().withMarketCatalogue(evt.getCatalogue()));

        b.setCommandHandler(BetfairCommand.AddMarketData.class, (cmd, ctx) ->
            ctx.thenPersist(new BetfairEvent.MarketDataAdded(cmd.getData()),
                    evt -> ctx.reply(Done.getInstance())));

        b.setEventHandler(BetfairEvent.MarketDataAdded.class,
                evt -> state().withMarketData(evt.getData()));

        return b.build();
    }
}
