package com.betfair.aping.entities;

import lombok.Value;

@Value
public class EventTypeResult {

	private EventType eventType;

	private int marketCount;
}
