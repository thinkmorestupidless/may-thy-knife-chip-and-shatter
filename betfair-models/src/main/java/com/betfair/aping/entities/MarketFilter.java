package com.betfair.aping.entities;

import com.betfair.aping.enums.MarketBettingType;
import com.betfair.aping.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class MarketFilter {

	private String textQuery;

	private Set<String> exchangeIds;

	private Set<String> eventTypeIds;

	private Set<String> marketIds;

	private Boolean inPlayOnly;

	private Set<String> eventIds;

	private Set<String> competitionIds;

	private Set<String> venues;

	private Boolean bspOnly;

	private Boolean turnInPlayEnabled;

	private Set<MarketBettingType> marketBettingTypes;

	private Set<String> marketCountries;

	private Set<String> marketTypeCodes;

	private TimeRange marketStartTime;

	private Set<OrderStatus> withOrders;
}
