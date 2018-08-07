package org.dlsu.arrowsmith.models;

import java.util.ArrayList;

public class Offering {
	private String offeringId;
	private String courseId;
	private String course_code;
	private String facultyId;
	private String degreeProgram;
	private String section;
	private String batch;
	private String maxStudentsEnrolled;
	private String currStudentsEnrolled;
	private String isNonAcad;
	private String isShs;
	private String isServiceCourse;
	private String servicedeptId;
	private String isMasters;
	private String isElective;
	private String electiveType;
	private String remarks;
	private String status;
	private String iteoEval;
	private String startYear;
	private String endYear;
	private String term;
	private String isPublished;

	private ArrayList<Days> days;
	private Course course;
	private Faculty faculty;
	private Timeframe timeframe;

	public Offering() {
		days = new ArrayList<Days>();
	}

	public String getOfferingId() {
		return offeringId;
	}

	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	public String getDegreeProgram() {
		return degreeProgram;
	}

	public void setDegreeProgram(String degreeProgram) {
		this.degreeProgram = degreeProgram;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getMaxStudentsEnrolled() {
		return maxStudentsEnrolled;
	}

	public void setMaxStudentsEnrolled(String maxStudentsEnrolled) {
		this.maxStudentsEnrolled = maxStudentsEnrolled;
	}

	public String getCurrStudentsEnrolled() {
		return currStudentsEnrolled;
	}

	public void setCurrStudentsEnrolled(String currStudentsEnrolled) {
		this.currStudentsEnrolled = currStudentsEnrolled;
	}

	public String getIsNonAcad() {
		return isNonAcad;
	}

	public void setIsNonAcad(String isNonAcad) {
		this.isNonAcad = isNonAcad;
	}

	public String getIsShs() {
		return isShs;
	}

	public void setIsShs(String isShs) {
		this.isShs = isShs;
	}

	public String getIsServiceCourse() {
		return isServiceCourse;
	}

	public void setIsServiceCourse(String isServiceCourse) {
		this.isServiceCourse = isServiceCourse;
	}

	public String getServicedeptId() {
		return servicedeptId;
	}

	public void setServicedeptId(String servicedeptId) {
		this.servicedeptId = servicedeptId;
	}

	public String getIsMasters() {
		return isMasters;
	}

	public void setIsMasters(String isMasters) {
		this.isMasters = isMasters;
	}

	public String getIsElective() {
		return isElective;
	}

	public void setIsElective(String isElective) {
		this.isElective = isElective;
	}

	public String getElectiveType() {
		return electiveType;
	}

	public void setElectiveType(String electiveType) {
		this.electiveType = electiveType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIteoEval() {
		return iteoEval;
	}

	public void setIteoEval(String iteoEval) {
		this.iteoEval = iteoEval;
	}

	public ArrayList<Days> getDays() {
		return days;
	}

	public void setDays(ArrayList<Days> days) {
		this.days = days;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Timeframe getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(Timeframe timeframe) {
		this.timeframe = timeframe;
	}

	public String getCourse_code() {
		return course_code;
	}

	public void setCourse_code(String course_code) {
		this.course_code = course_code;
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

	public String getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	
}
