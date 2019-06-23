package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

public class ManageCourseDTO
{
    private String courseCode;
    private String courseDesc;
    private String courseName;
    private double courseUnits;
    private String roomType;
    private int numHours;

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

    public int getNumHours() {
        return numHours;
    }

    public void setNumHours(int numHours) {
        this.numHours = numHours;
    }
}
