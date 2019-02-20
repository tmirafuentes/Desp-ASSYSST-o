package org.dlsu.arrowsmith.classes.dtos;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;

public class OfferingModifyDto {
    private Long offeringId;
    private String classStatus;
    private String classSection;
    private String startTime;
    private String endTime;
    private char day1;
    private char day2;
    private String roomCode;
    private String faculty;

    public Long getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(Long offeringId) {
        this.offeringId = offeringId;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }

    public String getClassSection() {
        return classSection;
    }

    public void setClassSection(String classSection) {
        this.classSection = classSection;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStartTimeParsed() {
        //System.out.println(startTime);
        return startTime.replace(":", "");
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEndTimeParsed() {
        return endTime.replace(":", "");
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Iterator getAllDays() {
        ArrayList<Character> allDays = new ArrayList<Character>();
        allDays.add(day1);
        allDays.add(day2);
        return allDays.iterator();
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

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "OfferingModifyDto{" +
                "offeringId=" + offeringId +
                ", classStatus='" + classStatus + '\'' +
                ", classSection='" + classSection + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", day1=" + day1 +
                ", day2=" + day2 +
                ", roomCode='" + roomCode + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }
}
