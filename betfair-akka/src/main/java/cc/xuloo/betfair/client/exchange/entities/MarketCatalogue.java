package cc.xuloo.betfair.client.exchange.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class MarketCatalogue {

	private String marketId;

	private String marketName;

	private MarketDescription description;

	private List<RunnerCatalog> runners;

	private EventType eventType;

	private Competition competition;

	private Event event;

	private BigDecimal totalMatched;

	@JsonCreator
	public MarketCatalogue(@JsonProperty("marketId") String marketId,
						   @JsonProperty("marketName") String marketName,
						   @JsonProperty("description") MarketDescription description,
						   @JsonProperty("runners") List<RunnerCatalog> runners,
						   @JsonProperty("eventType") EventType eventType,
						   @JsonProperty("competition") Competition competition,
						   @JsonProperty("event") Event event,
						   @JsonProperty("totalMatched") BigDecimal totalMatched) {
		this.marketId = marketId;
		this.marketName = marketName;
		this.description = description;
		this.runners = runners;
		this.eventType = eventType;
		this.competition = competition;
		this.event = event;
		this.totalMatched = totalMatched;
	}
}
