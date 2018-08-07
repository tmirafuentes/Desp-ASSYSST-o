package org.dlsu.arrowsmith.models;

public class Department {
    
    private String id;
    private String deptCode; 
    private String deptName;
    private String collegeID; 
    private String deptSize; 
    private String isObsolete;
    
    public Department(){
        
    }
    
    public Department(String id, String collegeID, String deptName, String deptCode, String deptSize, String isObsolete){
        this.id = id;
        this.collegeID = collegeID;
        this.deptName = deptName;
        this.deptCode = deptCode;
        this.deptSize = deptSize;
        this.isObsolete = isObsolete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCollegeID() {
        return collegeID;
    }

    public void setCollegeID(String collegeID) {
        this.collegeID = collegeID;
    }

    public String getDeptSize() {
        return deptSize;
    }

    public void setDeptSize(String deptSize) {
        this.deptSize = deptSize;
    }

    public String getIsObsolete() {
        return isObsolete;
    }

    public void setIsObsolete(String isObsolete) {
        this.isObsolete = isObsolete;
    }
    
}
