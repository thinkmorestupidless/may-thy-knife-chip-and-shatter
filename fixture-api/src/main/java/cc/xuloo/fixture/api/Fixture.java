package cc.xuloo.fixture.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.Value;

import java.util.Date;

@Value
@JsonDeserialize
public class Fixture {

    public final String fixtureId;

    public final String name;

    public final String countryCode;

    public final String timezone;

    public final String venue;

    public final Date openDate;

    @JsonCreator
    public Fixture(String fixtureId, String name, String countryCode, String timezone, String venue, Date openDate) {
        this.fixtureId      = Preconditions.checkNotNull(fixtureId, "fixtureId");
        this.name           = Preconditions.checkNotNull(name, "name");
        this.countryCode    = Preconditions.checkNotNull(countryCode, "countryCode");
        this.timezone       = Preconditions.checkNotNull(timezone, "timezone");
        this.venue          = Preconditions.checkNotNull(venue, "venue");
        this.openDate       = Preconditions.checkNotNull(openDate, "openDate");
    }
}
