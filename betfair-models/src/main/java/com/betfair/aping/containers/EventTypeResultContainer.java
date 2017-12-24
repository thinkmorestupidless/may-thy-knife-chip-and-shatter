package com.betfair.aping.containers;

import com.betfair.aping.entities.EventTypeResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EventTypeResultContainer extends Container {

	private final String id;
	
	private final List<EventTypeResult> result;

	@JsonCreator
    public EventTypeResultContainer(@JsonProperty("id") String id,
                                    @JsonProperty("result") List<EventTypeResult> result) {
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }
		
	public List<EventTypeResult> getResult() {
        return result;
    }
}
