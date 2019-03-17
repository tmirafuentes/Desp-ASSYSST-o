package org.dlsu.arrowsmith.classes.dtos;

public class FacultyLoadDto {
    public double getAdminLoad() {
        return adminLoad;
    }

    public void setAdminLoad(double adminLoad) {
        this.adminLoad = adminLoad;
    }

    public double getResearchLoad() {
        return researchLoad;
    }

    public void setResearchLoad(double researchLoad) {
        this.researchLoad = researchLoad;
    }

    public double getTeachingLoad() {
        return teachingLoad;
    }

    public void setTeachingLoad(double teachingLoad) {
        this.teachingLoad = teachingLoad;
    }

    public double getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(double totalLoad) {
        this.totalLoad = totalLoad;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private double adminLoad;
    private double researchLoad;
    private double teachingLoad;
    private double totalLoad;
    private String firstName;
    private String lastName;
}
