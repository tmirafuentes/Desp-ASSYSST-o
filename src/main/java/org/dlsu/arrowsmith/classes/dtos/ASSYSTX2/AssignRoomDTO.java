package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

public class AssignRoomDTO
{
    private Long offeringID;
    private String courseCode;
    private String section;
    private String roomCode;
    private char day1;
    private char day2;
    private String startTimeDay1;
    private String endTimeDay1;
    private String startTimeDay2;
    private String endTimeDay2;

    public Long getOfferingID() {
        return offeringID;
    }

    public void setOfferingID(Long offeringID) {
        this.offeringID = offeringID;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
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

    public String getStartTimeDay1() {
        return startTimeDay1;
    }

    public void setStartTimeDay1(String startTimeDay1) {
        this.startTimeDay1 = startTimeDay1;
    }

    public String getEndTimeDay1() {
        return endTimeDay1;
    }

    public void setEndTimeDay1(String endTimeDay1) {
        this.endTimeDay1 = endTimeDay1;
    }

    public String getStartTimeDay2() {
        return startTimeDay2;
    }

    public void setStartTimeDay2(String startTimeDay2) {
        this.startTimeDay2 = startTimeDay2;
    }

    public String getEndTimeDay2() {
        return endTimeDay2;
    }

    public void setEndTimeDay2(String endTimeDay2) {
        this.endTimeDay2 = endTimeDay2;
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
}
