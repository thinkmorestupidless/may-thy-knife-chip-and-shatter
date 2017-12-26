package cc.xuloo.betfair.aping.containers;

import cc.xuloo.betfair.aping.entities.Error;

public class Container {
	
	private Error error;

	private String jsonrpc;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
}
