package org.dlsu.arrowsmith.models;

public class DeloadOffer {
	
	private String deloadofferId;
	private Deloading deloading;
	private String facultyId;
	private String term;
	private String start_year;
	private String end_year;
	private String remarks;
	
	public String getDeloadofferId() {
		return deloadofferId;
	}
	public void setDeloadofferId(String deloadofferId) {
		this.deloadofferId = deloadofferId;
	}
	public Deloading getDeloading() {
		return deloading;
	}
	public void setDeloading(Deloading deloading) {
		this.deloading = deloading;
	}
	public String getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getStart_year() {
		return start_year;
	}
	public void setStart_year(String start_year) {
		this.start_year = start_year;
	}
	public String getEnd_year() {
		return end_year;
	}
	public void setEnd_year(String end_year) {
		this.end_year = end_year;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
