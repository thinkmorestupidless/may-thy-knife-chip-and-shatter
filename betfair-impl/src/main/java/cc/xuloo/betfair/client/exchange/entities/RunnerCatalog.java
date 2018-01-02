package cc.xuloo.betfair.client.exchange.entities;

public class RunnerCatalog {

	private Integer sortPriority;
	private Long selectionId;
	private String runnerName;
	private Double handicap;

	public Integer getSortPriority() {
		return sortPriority;
	}

	public void setSortPriority(Integer sortPriority) {
		this.sortPriority = sortPriority;
	}

	public Long getSelectionId() {
		return selectionId;
	}

	public void setSelectionId(Long selectionId) {
		this.selectionId = selectionId;
	}

	public String getRunnerName() {
		return runnerName;
	}

	public void setRunnerName(String runnerName) {
		this.runnerName = runnerName;
	}

	public Double getHandicap() {
		return handicap;
	}

	public void setHandicap(Double handicap) {
		this.handicap = handicap;
	}

	public String toString() {
		return "{" + "" + "selectionId=" + getSelectionId() + ","
				+ "runnerName=" + getRunnerName() + "," + "handicap="
				+ getHandicap() + "," + "}";
	}

}
