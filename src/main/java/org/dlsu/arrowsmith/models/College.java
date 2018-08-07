package org.dlsu.arrowsmith.models;

public class College {
    
    String id, collegeCode, collegeName, isObsolete;
    
    public College(){
        
    }
    
    public College(String id, String collegeCode, String collegeName, String isObsolete){
        this.id = id;
        this.collegeCode = collegeCode;
        this.collegeName = collegeName;
        this.isObsolete = isObsolete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getIsObsolete() {
        return isObsolete;
    }

    public void setIsObsolete(String isObsolete) {
        this.isObsolete = isObsolete;
    }
    
}
