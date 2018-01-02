package cc.xuloo.betfair.client.exchange.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APINGException extends Throwable {

	private final String errorDetails;

	private final String errorCode;

	private final String requestUUID;

	public APINGException(@JsonProperty("errorDetails") String errorDetails,
                          @JsonProperty("errorCode") String errorCode,
                          @JsonProperty("requestUUID") String requestUUID) {
	    this.errorDetails = errorDetails;
	    this.errorCode = errorCode;
	    this.requestUUID = requestUUID;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getRequestUUID() {
        return requestUUID;
    }
}
