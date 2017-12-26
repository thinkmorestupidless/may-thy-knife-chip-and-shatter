package cc.xuloo.betfair.aping.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Value;

@Value
public class EventResult {

	private final Event event;

	private final int marketCount;

	@JsonCreator
	public EventResult(Event event, int marketCount) {
		this.event = event;
		this.marketCount = marketCount;
	}
}
