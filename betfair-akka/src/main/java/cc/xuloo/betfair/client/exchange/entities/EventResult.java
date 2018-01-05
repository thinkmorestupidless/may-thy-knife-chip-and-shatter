package cc.xuloo.betfair.client.exchange.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class EventResult {

	private final Event event;

	private final int marketCount;

	@JsonCreator
	public EventResult(@JsonProperty("event") Event event,
					   @JsonProperty("marketCount") int marketCount) {
		this.event = event;
		this.marketCount = marketCount;
	}
}
