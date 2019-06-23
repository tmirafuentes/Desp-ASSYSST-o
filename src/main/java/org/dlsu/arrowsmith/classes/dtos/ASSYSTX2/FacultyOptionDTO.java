package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

public class FacultyOptionDTO
{
    private Long facultyID;
    private String facultyName;
    private double teachingUnits;
    private double deloadedUnits;
    private String facultyPosition;
    private String imageLink;

    public Long getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(Long facultyID) {
        this.facultyID = facultyID;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public double getTeachingUnits() {
        return teachingUnits;
    }

    public void setTeachingUnits(double teachingUnits) {
        this.teachingUnits = teachingUnits;
    }

    public double getDeloadedUnits() {
        return deloadedUnits;
    }

    public void setDeloadedUnits(double deloadedUnits) {
        this.deloadedUnits = deloadedUnits;
    }

    public String getFacultyPosition() {
        return facultyPosition;
    }

    public void setFacultyPosition(String facultyPosition) {
        this.facultyPosition = facultyPosition;
    }
}
