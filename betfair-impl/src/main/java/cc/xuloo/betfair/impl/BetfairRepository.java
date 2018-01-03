package cc.xuloo.betfair.impl;

import akka.Done;
import cc.xuloo.utils.CompletionStageUtils;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.PSequence;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import static com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide.completedStatement;

public class BetfairRepository {

    private final CassandraSession session;

    @Inject
    public BetfairRepository(CassandraSession session, ReadSide readSide) {
        this.session = session;

        readSide.register(BetfairEventProcessor.class);
    }

    private static class BetfairEventProcessor extends ReadSideProcessor<BetfairEvent> {

        private final CassandraSession session;
        private final CassandraReadSide readSide;

        private PreparedStatement insertMarketStatement;
        private PreparedStatement deleteMarketStatement;

        @javax.inject.Inject
        public BetfairEventProcessor(CassandraSession session, CassandraReadSide readSide) {
            this.session = session;
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<BetfairEvent> buildHandler() {
            return null;
        }

        private CompletionStage<Done> createTable() {
            return session.executeCreateTable(
                    "CREATE TABLE IF NOT EXISTS activeMarkets (" +
                            "marketId text," +
                            "callSign text," +
                            "PRIMARY KEY (marketId)" +
                            ")"
            );
        }

        private CompletionStage<Done> prepareStatements() {

            return CompletionStageUtils.doAll(

                    session.prepare(
                            "INSERT INTO activeMarkets(" +
                                    "marketId," +
                                    "callSign" +
                                    ") VALUES (?, ?)"
                    )
                            .thenAccept(statement -> insertMarketStatement = statement),

                    session.prepare(
                            "DELETE FROM activeMarkets" +
                                    " WHERE marketId = ?"
                    )
                            .thenAccept(statement -> deleteMarketStatement = statement)
            );
        }

        private CompletionStage<List<BoundStatement>> insertFlight(UUID flightId, String callsign) {
            return completedStatement(insertMarketStatement.bind(flightId, callsign));
        }

        private CompletionStage<List<BoundStatement>> deleteFlight(UUID flightId) {
            return completedStatement(deleteMarketStatement.bind(flightId));
        }

        @Override
        public PSequence<AggregateEventTag<BetfairEvent>> aggregateTags() {
            return BetfairEvent.TAG.allTags();
        }
    }
}
