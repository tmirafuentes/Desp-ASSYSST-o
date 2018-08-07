package org.dlsu.arrowsmith.models;

public class Faculty extends User{
	
	private String facultyId;
	private String yearsOfService;
	private String facultyType;
	private String status; //if Searched or Recommended
	private String teachCount;
	
	private Load load;
	private User user;
	
	public Faculty() {
		
	}
	
	public Faculty(User user, String yearsOfService, String facultyType) {
		super(user.getUserId(), user.getCollegeID(), user.getDeptID(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getUserType(), user.getUserPassword(), user.getDepartment(), user.getCollege());
		this.yearsOfService = yearsOfService;
		this.facultyType = facultyType;
	}
	
	public Faculty(User user, String id, String yearsOfService, String facultyType) {
		super(user.getUserId(), user.getCollegeID(), user.getDeptID(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getUserType(), user.getUserPassword(), user.getDepartment(), user.getCollege());
		this.facultyId = id;
		this.yearsOfService = yearsOfService;
		this.facultyType = facultyType;
		this.status = "Searched";
		this.teachCount = "N/A";
		this.user = user;
	}
	
	public Faculty(User user, String id, String yearsOfService, String facultyType, String status, String teachCount) {
		super(user.getUserId(), user.getCollegeID(), user.getDeptID(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getUserType(), user.getUserPassword(), user.getDepartment(), user.getCollege());
		this.facultyId = id;
		this.yearsOfService = yearsOfService;
		this.facultyType = facultyType;
		this.status = status;
		this.teachCount = teachCount;
	}

	public String getYearsOfService() {
		return yearsOfService;
	}

	public void setYearsOfService(String yearsOfService) {
		this.yearsOfService = yearsOfService;
	}

	public String getFacultyType() {
		return facultyType;
	}

	public void setFacultyType(String facultyType) {
		this.facultyType = facultyType;
	}

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTeachCount() {
		return teachCount;
	}

	public void setTeachCount(String teachCount) {
		this.teachCount = teachCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}