package cc.xuloo.betfair.aping.containers;

import cc.xuloo.betfair.aping.entities.MarketBook;

import java.util.List;

public class ListMarketBooksContainer extends Container{
	
	private List<MarketBook> result;
		
	public List<MarketBook> getResult() {
		return result;
	}
	public void setResult(List<MarketBook> result) {
		this.result = result;
	}
}
