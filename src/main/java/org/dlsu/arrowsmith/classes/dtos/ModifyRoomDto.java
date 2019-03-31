package org.dlsu.arrowsmith.classes.dtos;

public class ModifyRoomDto {
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

    private char day1;
    private char day2;
    private String startTime;
    private String endTime;


}
