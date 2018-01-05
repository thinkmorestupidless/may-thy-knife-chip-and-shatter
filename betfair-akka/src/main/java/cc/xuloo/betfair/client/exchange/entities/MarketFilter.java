package cc.xuloo.betfair.client.exchange.entities;

import cc.xuloo.betfair.client.exchange.enums.MarketBettingType;
import cc.xuloo.betfair.client.exchange.enums.OrderStatus;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class MarketFilter {

	private String textQuery;

	@Singular
	private Set<String> exchangeIds;

	@Singular
	private Set<String> eventTypeIds;

	@Singular
	private Set<String> marketIds;

	private Boolean inPlayOnly;

	@Singular
	private Set<String> eventIds;

	@Singular
	private Set<String> competitionIds;

	@Singular
	private Set<String> venues;

	private Boolean bspOnly;

	private Boolean turnInPlayEnabled;

	@Singular
	private Set<MarketBettingType> marketBettingTypes;

    @Singular
	private Set<String> marketCountries;

    @Singular
	private Set<String> marketTypeCodes;

	private TimeRange marketStartTime;

    @Singular
	private Set<OrderStatus> withOrders;
}
