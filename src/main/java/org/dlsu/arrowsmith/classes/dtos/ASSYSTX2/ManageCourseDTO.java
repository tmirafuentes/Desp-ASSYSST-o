package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

public class ManageCourseDTO
{
    private String courseCode;
    private String courseName;
    private double courseUnits;
    private String courseDesc;
    private String roomType;
    private double numHours;
    private String deptName;
    private String college;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCourseUnits() {
        return courseUnits;
    }

    public void setCourseUnits(double courseUnits) {
        this.courseUnits = courseUnits;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getNumHours() {
        return numHours;
    }

    public void setNumHours(double numHours) {
        this.numHours = numHours;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
