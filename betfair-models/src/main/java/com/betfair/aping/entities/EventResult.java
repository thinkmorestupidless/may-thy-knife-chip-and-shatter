package com.betfair.aping.entities;

import lombok.Value;

@Value
public class EventResult {

	private Event event;

	private int marketCount;

}
