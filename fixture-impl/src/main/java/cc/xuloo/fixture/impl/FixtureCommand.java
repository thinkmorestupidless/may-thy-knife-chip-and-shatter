/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Value;

import java.util.Optional;

/**
 * This interface defines all the commands that the LessstupidflightsEntity supports.
 * 
 * By convention, the commands should be inner classes of the interface, which
 * makes it simple to get a complete picture of what commands an entity
 * supports.
 */
public interface FixtureCommand extends Jsonable {

  @Value
  @JsonDeserialize
  final class AddFixture implements FixtureCommand, CompressedJsonable, PersistentEntity.ReplyType<String> {

    public final String callsign;

    public final String equipment;

    public final String departureIata;

    public final String arrivalIata;

    @JsonCreator
    public AddFixture(String callsign, String equipment, String departureIata, String arrivalIata) {
      this.callsign = callsign;
      this.equipment = equipment;
      this.departureIata = departureIata;
      this.arrivalIata = arrivalIata;
    }
  }

  @Value
  @JsonDeserialize
  final class AddPassenger implements FixtureCommand, PersistentEntity.ReplyType<String> {

    public final String passengerId;

    public final String lastName;

    public final String firstName;

    public final String initial;

    public final Optional<String> seatAssignment;

    @JsonCreator
    public AddPassenger(String passengerId, String lastName, String firstName, String initial, Optional<String> seatAssignment) {
      this.passengerId = passengerId;
      this.lastName = lastName;
      this.firstName = firstName;
      this.initial = initial;
      this.seatAssignment = seatAssignment;
    }
  }

  @Value
  @JsonDeserialize
  final class SelectSeat implements FixtureCommand, PersistentEntity.ReplyType<Done> {

    public final String passengerId;

    public final String seatAssignment;

    @JsonCreator
    public SelectSeat(String passengerId, String seatAssignment) {
      this.passengerId = passengerId;
      this.seatAssignment = seatAssignment;
    }
  }

  @Value
  @JsonDeserialize
  final class RemovePassenger implements FixtureCommand, PersistentEntity.ReplyType<Done> {

    public final String passengerId;

    @JsonCreator
    public RemovePassenger(String passengerId) {
      this.passengerId = passengerId;
    }
  }

  @Value
  @JsonDeserialize
  final class CloseFixture implements FixtureCommand, PersistentEntity.ReplyType<Done> {

    public final String flightId;

    @JsonCreator
    public CloseFixture(String flightId) {
      this.flightId = flightId;
    }
  }

}
