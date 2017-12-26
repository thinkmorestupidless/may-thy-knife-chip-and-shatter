package cc.xuloo.betfair.aping.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	private final cc.xuloo.betfair.aping.exceptions.APINGException APINGException;

	@JsonCreator
	public Data(@JsonProperty("APINGException") cc.xuloo.betfair.aping.exceptions.APINGException APINGException) {
		this.APINGException = APINGException;
	}

	public cc.xuloo.betfair.aping.exceptions.APINGException getAPINGException() {
		return APINGException;
	}
}
