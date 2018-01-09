package cc.xuloo.betfair.client.exchange.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

	private final Data data;

	@JsonCreator
	public Error(@JsonProperty("data") Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}
}
