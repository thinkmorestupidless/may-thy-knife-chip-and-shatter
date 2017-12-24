package cc.xuloo.fixture.impl;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;
import org.assertj.core.api.Assertions;
import org.junit.*;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class FixtureEntityTest {

  private static ActorSystem system;

  private PersistentEntityTestDriver<FixtureCommand, FixtureEvent, FixtureState> driver;

  private static final UUID flightId          = UUID.randomUUID();
  private static final String DateTimePattern = "yyyy-MM-dd HH:mm:ss";
  private static final String callsign        = "UA100";
  private static final String equipment       = "757-800";
  private static final String departure       = "EWR";
  private static final String arrival         = "SFO";

  private static final Passenger passenger1   = new Passenger(UUID.randomUUID().toString(), "Walsh", "Sean", "A", Optional.of("1A"));
  private static final Passenger passenger2   = new Passenger(UUID.randomUUID().toString(), "Smith", "John", "P", Optional.empty());


  @BeforeClass
  public static void setup() {
    system = ActorSystem.create("FixtureEntityTest");
  }

  @AfterClass
  public static void teardown() {
    JavaTestKit.shutdownActorSystem(system);
    system = null;
  }

  @Before
  public void createTestDriver() {
    driver = new PersistentEntityTestDriver<>(system, new FixtureEntity(), flightId.toString());
  }

  @After
  public void noIssues() {
    if (!driver.getAllIssues().isEmpty()) {
      driver.getAllIssues().forEach(System.out::println);
      fail("There were issues " + driver.getAllIssues().get(0));
    }
  }

  private void withDriverAndFlight(BiConsumer<PersistentEntityTestDriver<FixtureCommand, FixtureEvent, FixtureState>, Outcome<FixtureEvent, FixtureState>> block) {
    block.accept(driver, driver.run(new FixtureCommand.AddFixture(callsign, equipment, departure, arrival)));
  }

  @Test
  public void shouldCreateNewFlight() {
    withDriverAndFlight((driver, flight) -> {
      Assertions.assertThat(flight.events()).containsOnly(new FixtureEvent.FixtureAdded(flightId.toString(), callsign, equipment, departure, arrival));
      assertThat(flight.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.emptySet()));
    });
  }

  @Test
  public void shouldAddPassengerWithSeatAssignment() {
    withDriverAndFlight((driver, flight) -> {
      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.AddPassenger(passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.PassengerAdded(flightId.toString(), passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger1)));
    });
  }

  @Test
  public void shouldAddPassengerWithoutSeatAssignment() {
    withDriverAndFlight((driver, flight) -> {
      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.AddPassenger(passenger2.passengerId, passenger2.lastName, passenger2.firstName, passenger2.initial, passenger2.seatAssignment));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.PassengerAdded(flightId.toString(), passenger2.passengerId, passenger2.lastName, passenger2.firstName, passenger2.initial, passenger2.seatAssignment));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger2)));
    });
  }

  @Test
  public void shouldSelectSeat() {
    withDriverAndFlight((driver, flight) -> {
      String seatAssignment = "1B";

      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.AddPassenger(passenger2.passengerId, passenger2.lastName, passenger2.firstName, passenger2.initial, passenger2.seatAssignment));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.PassengerAdded(flightId.toString(), passenger2.passengerId, passenger2.lastName, passenger2.firstName, passenger2.initial, passenger2.seatAssignment));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger2)));

      Outcome<FixtureEvent, FixtureState> outcome2 = driver.run(new FixtureCommand.SelectSeat(passenger2.passengerId, seatAssignment));
      Assertions.assertThat(outcome2.events()).containsOnly(new FixtureEvent.SeatSelected(flightId.toString(), passenger2.passengerId, seatAssignment));
      assertThat(outcome2.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger2.withSeatAssignment(Optional.of(seatAssignment)))));
    });
  }

  @Test
  public void shouldSelectNewSeat() {
    withDriverAndFlight((driver, flight) -> {
      String seatAssignment = "1B";

      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.AddPassenger(passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.PassengerAdded(flightId.toString(), passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger1)));

      Outcome<FixtureEvent, FixtureState> outcome2 = driver.run(new FixtureCommand.SelectSeat(passenger1.passengerId, seatAssignment));
      Assertions.assertThat(outcome2.events()).containsOnly(new FixtureEvent.SeatSelected(flightId.toString(), passenger1.passengerId, seatAssignment));
      assertThat(outcome2.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger1.withSeatAssignment(Optional.of(seatAssignment)))));
    });
  }

  @Test
  public void shouldRemovePassengerFromOpenFlight() {
    withDriverAndFlight((driver, flight) -> {
      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.AddPassenger(passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.PassengerAdded(flightId.toString(), passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger1)));

      Outcome<FixtureEvent, FixtureState> outcome2 = driver.run(new FixtureCommand.RemovePassenger(passenger1.passengerId));
      Assertions.assertThat(outcome2.events()).containsOnly(new FixtureEvent.PassengerRemoved(flightId.toString(), passenger1.passengerId));
      assertThat(outcome2.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.emptySet()));
    });
  }

  @Test
  public void shouldCloseFlight() {
    withDriverAndFlight((driver, flight) -> {
      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.CloseFixture(flightId.toString()));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.FixtureClosed(flightId.toString()));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, true)), Collections.emptySet()));
    });
  }

  @Test
  public void shouldRemovePassengerFromClosedFlight() {
    withDriverAndFlight((driver, flight) -> {
      Outcome<FixtureEvent, FixtureState> outcome = driver.run(new FixtureCommand.AddPassenger(passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      Assertions.assertThat(outcome.events()).containsOnly(new FixtureEvent.PassengerAdded(flightId.toString(), passenger1.passengerId, passenger1.lastName, passenger1.firstName, passenger1.initial, passenger1.seatAssignment));
      assertThat(outcome.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, false)), Collections.singleton(passenger1)));

      Outcome<FixtureEvent, FixtureState> outcome2 = driver.run(new FixtureCommand.CloseFixture(flightId.toString()));
      Assertions.assertThat(outcome2.events()).containsOnly(new FixtureEvent.FixtureClosed(flightId.toString()));
      assertThat(outcome2.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, true)), Collections.singleton(passenger1)));

      Outcome<FixtureEvent, FixtureState> outcome3 = driver.run(new FixtureCommand.RemovePassenger(passenger1.passengerId));
      Assertions.assertThat(outcome3.events()).containsOnly(new FixtureEvent.PassengerRemoved(flightId.toString(), passenger1.passengerId));
      assertThat(outcome3.state()).isEqualTo(new FixtureState(Optional.of(new FlightInfo(flightId.toString(), callsign, equipment, departure, arrival, true)), Collections.emptySet()));
    });
  }
}
