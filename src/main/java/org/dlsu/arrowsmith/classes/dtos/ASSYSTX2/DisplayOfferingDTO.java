package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

public class DisplayOfferingDTO
{
    /* Course Offering Details */
    private Long offeringID;
    private String courseCode;
    private String section;
    private char day1;
    private char day2;
    private String startTime;
    private String endTime;
    private String roomCode;
    private String facultyName;

    public DisplayOfferingDTO() { }

    public Long getOfferingID() {
        return offeringID;
    }

    public void setOfferingID(Long offeringID) {
        this.offeringID = offeringID;
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

    public char getDay1() {
        return day1;
    }

    public void setDay1(char day1) {
        this.day1 = day1;
    }

    public char getDay2() {
        return day2;
    }

    public void setDay2(char day2) {
        this.day2 = day2;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
