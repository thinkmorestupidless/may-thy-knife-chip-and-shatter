package cc.xuloo.betfair.client.exchange.entities;

import lombok.Value;

@Value
public class EventTypeResult {

	private EventType eventType;

	private int marketCount;
}
