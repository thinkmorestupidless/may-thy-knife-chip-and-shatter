package com.betfair.aping.containers;

import com.betfair.aping.entities.EventResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EventResultContainer extends Container {

	private final String id;

	private final List<EventResult> result;

	@JsonCreator
    public EventResultContainer(@JsonProperty("id") String id,
                                @JsonProperty("result") List<EventResult> result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }
		
	public List<EventResult> getResult() {
        return result;
    }
}
