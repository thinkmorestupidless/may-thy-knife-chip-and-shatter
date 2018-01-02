package cc.xuloo.betfair.client.exchange.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.Date;

@Value
public class Event {

	private String id;

	private String name;

	private String countryCode;

	private String timezone;

	private String venue;

	private Date openDate;

	@ConstructorProperties({"id", "name", "countryCode", "timezone", "venue", "openDate"})
	@JsonCreator
	public Event(@JsonProperty("id") String id,
				 @JsonProperty("name") String name,
				 @JsonProperty("countryCode") String countryCode,
				 @JsonProperty("timezone") String timezone,
				 @JsonProperty("venue") String venue,
				 @JsonProperty("openDate") Date openDate) {
		this.id = id;
		this.name = name;
		this.countryCode = countryCode;
		this.timezone = timezone;
		this.venue = venue;
		this.openDate = openDate;
	}
}
