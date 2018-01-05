package cc.xuloo.betfair.client.exchange.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class MarketBook {

	private String marketId;

	private Boolean isMarketDataDelayed;

	private String status;

	private int betDelay;

	private Boolean bspReconciled;

	private Boolean complete;

	private Boolean inplay;

	private int numberOfWinners;

	private int numberOfRunners;

	private int numberOfActiveRunners;

	private Date lastMatchTime;

	private Double totalMatched;

	private Double totalAvailable;

	private Boolean crossMatching;

	private Boolean runnersVoidable;

	private Long version;

	private List<Runner> runners;

	@JsonCreator
	public MarketBook(@JsonProperty("marketId") String marketId,
					  @JsonProperty("isMarketDataDelayed") Boolean isMarketDataDelayed,
					  @JsonProperty("status") String status,
					  @JsonProperty("betDelay") int betDelay,
					  @JsonProperty("bspReconciled") Boolean bspReconciled,
					  @JsonProperty("complete") Boolean complete,
					  @JsonProperty("inplay") Boolean inplay,
					  @JsonProperty("numberOfWinners") int numberOfWinners,
					  @JsonProperty("numberOfRunners") int numberOfRunners,
					  @JsonProperty("numberOfActiveRunners") int numberOfActiveRunners,
					  @JsonProperty("lastMatchTime") Date lastMatchTime,
					  @JsonProperty("totalMatched") Double totalMatched,
					  @JsonProperty("totalAvailable") Double totalAvailable,
					  @JsonProperty("crossMatching") Boolean crossMatching,
					  @JsonProperty("runnersVoidable") Boolean runnersVoidable,
					  @JsonProperty("version") Long version,
					  @JsonProperty("runners") List<Runner> runners) {
		this.marketId = marketId;
		this.isMarketDataDelayed = isMarketDataDelayed;
		this.status = status;
		this.betDelay = betDelay;
		this.bspReconciled = bspReconciled;
		this.complete = complete;
		this.inplay = inplay;
		this.numberOfWinners = numberOfWinners;
		this.numberOfRunners = numberOfRunners;
		this.numberOfActiveRunners = numberOfActiveRunners;
		this.lastMatchTime = lastMatchTime;
		this.totalMatched = totalMatched;
		this.totalAvailable = totalAvailable;
		this.crossMatching = crossMatching;
		this.runnersVoidable = runnersVoidable;
		this.version = version;
		this.runners = runners;
	}
}
