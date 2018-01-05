/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.util.Optional;

/**
 * This interface defines all the events that the LessstupidflightsEntity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface FixtureEvent extends Jsonable, AggregateEvent<FixtureEvent> {

  /**
   * Tags are used for getting and publishing streams of events. Each event
   * will have this tag, and in this case, we are partitioning the tags into
   * 4 shards, which means we can have 4 concurrent processors/publishers of
   * events.
   */
  AggregateEventShards<FixtureEvent> TAG = AggregateEventTag.sharded(FixtureEvent.class, 4);

  @SuppressWarnings("serial")
  @Value
  @JsonDeserialize
  final class FixtureAdded implements FixtureEvent {

    public final String flightId;

    public final String callsign;

    public final String equipment;

    public final String departureIata;

    public final String arrivalIata;

    @JsonCreator
    public FixtureAdded(String flightId, String callsign, String equipment, String departureIata, String arrivalIata) {
      this.flightId = flightId;
      this.callsign = callsign;
      this.equipment = equipment;
      this.departureIata = departureIata;
      this.arrivalIata = arrivalIata;
    }
  }

  @SuppressWarnings("serial")
  @Value
  @JsonDeserialize
  final class PassengerAdded implements FixtureEvent {

    public final String flightId;

    public final String passengerId;

    public final String lastName;

    public final String firstName;

    public final String initial;

    public final Optional<String> seatAssignment;

    @JsonCreator
    public PassengerAdded(String flightId, String passengerId, String lastName, String firstName, String initial, Optional<String> seatAssignment) {
      this.flightId = flightId;
      this.passengerId = passengerId;
      this.lastName = lastName;
      this.firstName = firstName;
      this.initial = initial;
      this.seatAssignment = seatAssignment;
    }
  }

  @SuppressWarnings("serial")
  @Value
  @JsonDeserialize
  final class SeatSelected implements FixtureEvent {

    public final String flightId;

    public final String passengerId;

    public final String seatAssignment;

    @JsonCreator
    public SeatSelected(String flightId, String passengerId, String seatAssignment) {
      this.flightId = flightId;
      this.passengerId = passengerId;
      this.seatAssignment = seatAssignment;
    }
  }

  @SuppressWarnings("serial")
  @Value
  @JsonDeserialize
  final class PassengerRemoved implements FixtureEvent {

    public final String flightId;

    public final String passengerId;

    @JsonCreator
    public PassengerRemoved(String flightId, String passengerId) {
      this.flightId = flightId;
      this.passengerId = passengerId;
    }
  }

  @SuppressWarnings("serial")
  @Value
  @JsonDeserialize
  final class FixtureClosed implements FixtureEvent {

    public final String flightId;

    @JsonCreator
    public FixtureClosed(String flightId) {
      this.flightId = flightId;
    }
  }

  @Override
  default AggregateEventTagger<FixtureEvent> aggregateTag() {
    return TAG;
  }
}
