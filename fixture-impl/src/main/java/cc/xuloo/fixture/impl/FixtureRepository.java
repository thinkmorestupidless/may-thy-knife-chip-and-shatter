package cc.xuloo.fixture.impl;

import akka.Done;
import cc.xuloo.fixture.utils.CompletionStageUtils;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import cc.xuloo.fixture.api.Fixture;
import org.pcollections.PSequence;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide.completedStatement;

@Singleton
public class FixtureRepository {

    private final CassandraSession session;

    @Inject
    public FixtureRepository(CassandraSession session, ReadSide readSide) {
        this.session = session;

        readSide.register(FlightEventProcessor.class);
    }

    public CompletionStage<Set<Fixture>> listFixtures() {
        return session.selectAll(
                "SELECT * FROM fixtures"
                )
                .thenApply(List::stream)
                .thenApply(rows -> rows.map(FixtureRepository::toFixture))
                .thenApply(fixtures -> fixtures.collect(Collectors.toSet()));
    }

    private static Fixture toFixture(Row row) {
        return new Fixture(row.getString("id"), row.getString("name"), row.getString("countryCode"), row.getString("timezone"), row.getString("venue"), row.getTimestamp("openDate"));
    }

    private static class FlightEventProcessor extends ReadSideProcessor<FixtureEvent> {

        private final CassandraSession session;
        private final CassandraReadSide readSide;

        private PreparedStatement insertFlightStatement;
        private PreparedStatement deleteFlightStatement;

        @Inject
        public FlightEventProcessor(CassandraSession session, CassandraReadSide readSide) {
            this.session = session;
            this.readSide = readSide;
        }

        @Override
        public ReadSideHandler<FixtureEvent> buildHandler() {
            return readSide.<FixtureEvent>builder("fixtureEventOffset")
                           .setGlobalPrepare(this::createTable)
                           .setPrepare(tag -> prepareStatements())
                           .setEventHandler(FixtureEvent.FixtureAdded.class, e -> insertFlight(UUID.fromString(e.flightId), e.callsign))
                           .setEventHandler(FixtureEvent.FixtureClosed.class, e -> deleteFlight(UUID.fromString(e.flightId)))
                           .build();
        }

        private CompletionStage<Done> createTable() {
            return session.executeCreateTable(
                    "CREATE TABLE IF NOT EXISTS fixtures (" +
                            "flightId UUID," +
                            "callSign text," +
                            "PRIMARY KEY (flightId)" +
                          ")"
            );
        }

        private CompletionStage<Done> prepareStatements() {

            return CompletionStageUtils.doAll(

                        session.prepare(
                            "INSERT INTO fixtures(" +
                             "flightId," +
                             "callSign" +
                            ") VALUES (?, ?)"
                        )
                        .thenAccept(statement -> insertFlightStatement = statement),

                        session.prepare(
                                "DELETE FROM fixtures" +
                                " WHERE flightId = ?"
                        )
                        .thenAccept(statement -> deleteFlightStatement = statement)
                    );
        }

        private CompletionStage<List<BoundStatement>> insertFlight(UUID flightId, String callsign) {
            return completedStatement(insertFlightStatement.bind(flightId, callsign));
        }

        private CompletionStage<List<BoundStatement>> deleteFlight(UUID flightId) {
            return completedStatement(deleteFlightStatement.bind(flightId));
        }

        @Override
        public PSequence<AggregateEventTag<FixtureEvent>> aggregateTags() {
            return FixtureEvent.TAG.allTags();
        }
    }
}
