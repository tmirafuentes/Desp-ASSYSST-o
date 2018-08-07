package org.dlsu.arrowsmith.models;

public class User {
    
    private String userId;
    private String collegeID;  
    private String deptID;
    private String firstName;  
    private String middleName;  
    private String lastName;
    private String userType;  
    private String userPassword;
    Department department;
    College college;
    
    public User(){
        
    }
    
    public User(String userId, String collegeID, String deptID, String firstName, String middleName, String lastName, String userType, String userPassword){
        this.userId = userId;
        this.collegeID = collegeID;
        this.deptID = deptID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userType = userType;
        this.userPassword = userPassword;
    }
    
    public User(String userId, String collegeID, String deptID, String firstName, String middleName, String lastName, String userType, String userPassword, Department department, College college){
        this.userId = userId;
        this.collegeID = collegeID;
        this.deptID = deptID;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userType = userType;
        this.userPassword = userPassword;
        this.department = department;
        this.college = college;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(String collegeID) {
        this.collegeID = collegeID;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}
    
    
}
