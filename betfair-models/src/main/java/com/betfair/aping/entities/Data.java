package com.betfair.aping.entities;

import com.betfair.aping.exceptions.APINGException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	private final APINGException APINGException;

	@JsonCreator
	public Data(@JsonProperty("APINGException") com.betfair.aping.exceptions.APINGException APINGException) {
		this.APINGException = APINGException;
	}

	public com.betfair.aping.exceptions.APINGException getAPINGException() {
		return APINGException;
	}
}
