/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.util.Optional;

/**
 * This is an event sourced entity. It has a state, {@link FixtureState}
 */
public class FixtureEntity extends PersistentEntity<FixtureCommand, FixtureEvent, FixtureState> {

  /**
   * An entity can define different behaviours for different states, but it will
   * always start with an initial behaviour.
   */
  @Override
  public Behavior initialBehavior(Optional<FixtureState> snapshotState) {

    BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(FixtureState.empty()));

    return b.build();
  }
}
