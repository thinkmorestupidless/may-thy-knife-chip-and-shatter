package cc.xuloo.betfair.client.exchange.containers;

import cc.xuloo.betfair.client.exchange.entities.MarketCatalogue;

import java.util.List;

public class ListMarketCatalogueContainer extends Container {

	private Long id;

	private List<MarketCatalogue> result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<MarketCatalogue> getResult() {
		return result;
	}

	public void setResult(List<MarketCatalogue> result) {
		this.result = result;
	}

}
