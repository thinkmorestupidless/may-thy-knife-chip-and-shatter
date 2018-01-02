package cc.xuloo.betfair.client.exchange.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	private final cc.xuloo.betfair.client.exchange.exceptions.APINGException APINGException;

	@JsonCreator
	public Data(@JsonProperty("APINGException") cc.xuloo.betfair.client.exchange.exceptions.APINGException APINGException) {
		this.APINGException = APINGException;
	}

	public cc.xuloo.betfair.client.exchange.exceptions.APINGException getAPINGException() {
		return APINGException;
	}
}
