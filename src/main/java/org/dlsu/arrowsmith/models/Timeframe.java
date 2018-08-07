package org.dlsu.arrowsmith.models;

public class Timeframe {
	private String startYear;
	private String endYear;
	private String term;

	public Timeframe() {
		
	}
	
	public Timeframe(String startYear, String endYear, String term) {
		this.startYear = startYear;
		this.endYear = endYear;
		this.term = term;
	}
	
	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String makeString() {
		String res = "AY " + this.startYear + "-" + this.endYear + " T" + this.term;		
		
		return res;
	}
}
