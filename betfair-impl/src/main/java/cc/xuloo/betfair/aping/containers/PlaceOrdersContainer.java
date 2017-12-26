package cc.xuloo.betfair.aping.containers;


import cc.xuloo.betfair.aping.entities.PlaceExecutionReport;

public class PlaceOrdersContainer extends Container {

	private PlaceExecutionReport result;
	
	public PlaceExecutionReport getResult() {
		return result;
	}
	
	public void setResult(PlaceExecutionReport result) {
		this.result = result;
	}

}
