/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package cc.xuloo.fixture.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The state for the {@link FixtureEntity} entity.
 */
@SuppressWarnings("serial")
@Value
@JsonDeserialize
public final class FixtureState implements CompressedJsonable {

  private final String fixtureId;

  private final String name;

  private final String countryCode;

  private final String timezone;

  private final String venue;

  private final Date openDate;

  public static FixtureState empty() {
      return new FixtureState("", "", "", "", "", new Date(0));
  }

  @JsonCreator
  public FixtureState(String fixtureId, String name, String countryCode, String timezone, String venue, Date openDate) {
    this.fixtureId      = Preconditions.checkNotNull(fixtureId, "fixtureId");
    this.name           = Preconditions.checkNotNull(name, "name");
    this.countryCode    = Preconditions.checkNotNull(countryCode, "countryCode");
    this.timezone       = Preconditions.checkNotNull(timezone, "timezone");
    this.venue          = Preconditions.checkNotNull(venue, "venue");
    this.openDate       = Preconditions.checkNotNull(openDate, "openDate");
  }
}
