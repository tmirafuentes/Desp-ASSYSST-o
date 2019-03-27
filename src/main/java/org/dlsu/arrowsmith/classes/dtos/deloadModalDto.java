package org.dlsu.arrowsmith.classes.dtos;

public class deloadModalDto {
    private String facultyName;

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDeloadType() {
        return deloadType;
    }

    public void setDeloadType(String deloadType) {
        this.deloadType = deloadType;
    }

    private String deloadType;

}
