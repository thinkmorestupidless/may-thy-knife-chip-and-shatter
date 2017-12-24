package cc.xuloo.fixture.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import lombok.Value;

import java.util.Date;

@Value
@JsonDeserialize
public class Fixture {

    public final String id;

    public final String name;

    public final String countryCode;

    public final String timezone;

    public final String venue;

    public final Date openDate;

    @JsonCreator
    public Fixture(String id, String name, String countryCode, String timezone, String venue, Date openDate) {
        this.id             = Preconditions.checkNotNull(id, "callsign");
        this.name           = Preconditions.checkNotNull(name, "callsign");
        this.countryCode    = Preconditions.checkNotNull(countryCode, "equipment");
        this.timezone       = Preconditions.checkNotNull(timezone, "departureIata");
        this.venue          = Preconditions.checkNotNull(venue, "arrivalIata");
        this.openDate       = Preconditions.checkNotNull(openDate, "arrivalIata");
    }
}
