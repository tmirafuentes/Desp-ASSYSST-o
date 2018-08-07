package org.dlsu.arrowsmith.models;

public class TempOffering {
	private String degreeProgram;
	private String courseId;
	private String courseCode;
	private String section;
	private String startYear;
	private String endYear;
	private String status;
	private String batch;
	private String remarks;
	private String room;
	private String facultyId;
	private String term;
	private String time1;
	private String time2;
	private String[] days1;
	private String[] days2;
	
	public TempOffering(){
		
	}
	
	public TempOffering(String startYear, String endYear, String term, String degreeProgram, String courseId, String courseCode, String section,
			String batch, String status, String remarks, String room, String facultyId, String[] days1, String[] days2, String time1, String time2){
		this.startYear = startYear;
		this.endYear = endYear;
		this.term = term;
		this.degreeProgram = degreeProgram;
		this.courseId = courseId;
		this.courseCode = courseCode;
		this.section = section;
		this.batch = batch;
		this.status = status;
		this.facultyId = facultyId;
		this.remarks = remarks;
		this.room = room;
		this.days1 = days1;
		this.days2 = days2;
		this.time1 = time1;
		this.time2 = time2;
	}

	public String getDegreeProgram() {
		return degreeProgram;
	}

	public void setDegreeProgram(String degreeProgram) {
		this.degreeProgram = degreeProgram;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String[] getDays1() {
		return days1;
	}

	public void setDays1(String[] days1) {
		this.days1 = days1;
	}

	public String[] getDays2() {
		return days2;
	}

	public void setDays2(String[] days2) {
		this.days2 = days2;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}
	
}

