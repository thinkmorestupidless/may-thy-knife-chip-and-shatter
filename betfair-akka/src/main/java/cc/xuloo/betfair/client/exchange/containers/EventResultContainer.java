package cc.xuloo.betfair.client.exchange.containers;

import cc.xuloo.betfair.client.exchange.entities.EventResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties
public class EventResultContainer extends Container {

    private final String jsonrpc;

	private final String id;

	private final List<EventResult> result;

	@JsonCreator
    public EventResultContainer(@JsonProperty("jsonrpc") String jsonrpc,
                                @JsonProperty("id") String id,
                                @JsonProperty("result") List<EventResult> result) {
	    this.jsonrpc = jsonrpc;
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
