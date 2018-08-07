package org.dlsu.arrowsmith.models;

public class Course {
	private String courseId;
	private String collegeId;
	private String deptId;
	private String areaId;
	private String courseCode;
	private String courseName;
	private String units;
	private String remarks;
	private String description;
	
	private College college;
	private Department department;

	public Course() {
		
	}
	
	public Course(String collegeId, String deptId, String areaId, String courseCode, String courseName, String units, String remarks, String description) {
		this.collegeId = collegeId;
		this.deptId = deptId;
		this.areaId = areaId;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.units = units;
		this.remarks = remarks;
		this.description = description;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

}
