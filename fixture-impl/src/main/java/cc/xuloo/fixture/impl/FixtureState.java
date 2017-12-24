/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The state for the {@link FixtureEntity} entity.
 */
@SuppressWarnings("serial")
@Value
@JsonDeserialize
public final class FixtureState implements CompressedJsonable {

  public final Optional<FlightInfo> flightInfo;

  public final Set<Passenger> passengers;

  @JsonCreator
  public FixtureState(Optional<FlightInfo> flightInfo, Set<Passenger> passengers) {
    this.flightInfo = flightInfo;
    this.passengers = passengers;
  }

  public static FixtureState empty() {
    return new FixtureState(Optional.empty(), Collections.emptySet());
  }

  public FixtureState withPassenger(Passenger passenger) {
    Set<Passenger> copy = new HashSet<>(passengers);
    copy.add(passenger);

    return new FixtureState(flightInfo, copy);
  }

  public FixtureState updatePassenger(Passenger passenger) {
    Set<Passenger> updated = passengers.stream().filter(p -> !p.passengerId.equals(passenger.passengerId)).collect(Collectors.toSet());
    updated.add(passenger);

    return new FixtureState(flightInfo, updated);
  }

  public FixtureState withoutPassenger(String passengerId) {
    return new FixtureState(flightInfo, passengers.stream().filter(p -> !p.passengerId.equals(passengerId)).collect(Collectors.toSet()));
  }

  public FixtureState withDoorsClosed(Boolean doorsClosed) {
    return new FixtureState(Optional.of(flightInfo.get().withDoorsClosed(doorsClosed)), passengers);
  }
}
