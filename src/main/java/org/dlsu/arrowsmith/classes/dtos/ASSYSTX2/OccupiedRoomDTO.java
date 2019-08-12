package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

public class OccupiedRoomDTO
{
    private String roomCode;
    private String offDay1;
    private String offDay2;
    private String offDay3;
    private String offDay4;
    private String offDay5;
    private String offDay6;
    private boolean availDay1;
    private boolean availDay2;
    private boolean availDay3;
    private boolean availDay4;
    private boolean availDay5;
    private boolean availDay6;

    public OccupiedRoomDTO(String roomCode)
    {
        this.roomCode = roomCode;
        this.offDay1 = this.offDay2 = this.offDay3 = this.offDay4 = this.offDay5 = this.offDay6 = "";
        this.availDay1 = this.availDay2 = this.availDay3 = this.availDay4 = this.availDay5 = this.availDay6 = true;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getOffDay1() {
        return offDay1;
    }

    public void setOffDay1(String offDay1) {
        this.offDay1 = offDay1;
    }

    public String getOffDay2() {
        return offDay2;
    }

    public void setOffDay2(String offDay2) {
        this.offDay2 = offDay2;
    }

    public String getOffDay3() {
        return offDay3;
    }

    public void setOffDay3(String offDay3) {
        this.offDay3 = offDay3;
    }

    public String getOffDay4() {
        return offDay4;
    }

    public void setOffDay4(String offDay4) {
        this.offDay4 = offDay4;
    }

    public String getOffDay5() {
        return offDay5;
    }

    public void setOffDay5(String offDay5) {
        this.offDay5 = offDay5;
    }

    public String getOffDay6() {
        return offDay6;
    }

    public void setOffDay6(String offDay6) {
        this.offDay6 = offDay6;
    }

    public boolean isAvailDay1() {
        return availDay1;
    }

    public void setAvailDay1(boolean availDay1) {
        this.availDay1 = availDay1;
    }

    public boolean isAvailDay2() {
        return availDay2;
    }

    public void setAvailDay2(boolean availDay2) {
        this.availDay2 = availDay2;
    }

    public boolean isAvailDay3() {
        return availDay3;
    }

    public void setAvailDay3(boolean availDay3) {
        this.availDay3 = availDay3;
    }

    public boolean isAvailDay4() {
        return availDay4;
    }

    public void setAvailDay4(boolean availDay4) {
        this.availDay4 = availDay4;
    }

    public boolean isAvailDay5() {
        return availDay5;
    }

    public void setAvailDay5(boolean availDay5) {
        this.availDay5 = availDay5;
    }

    public boolean isAvailDay6() {
        return availDay6;
    }

    public void setAvailDay6(boolean availDay6) {
        this.availDay6 = availDay6;
    }
}
