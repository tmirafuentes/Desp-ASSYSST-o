package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

import java.util.ArrayList;
import java.util.Iterator;

public class ManageFacultyDTO
{
    private String facultyName;
    private String facultyType;
    private String department;
    private String active;
    private Iterator teachingLoad;
    private Iterator researchLoad;
    private Iterator adminLoad;
    private double teachingUnits;
    private double researchUnits;
    private double adminUnits;
    private double totalUnits;
    private double numPreparations;

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyType() {
        return facultyType;
    }

    public void setFacultyType(String facultyType) {
        this.facultyType = facultyType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Iterator getTeachingLoad() {
        return teachingLoad;
    }

    public void setTeachingLoad(Iterator teachingLoad) {
        this.teachingLoad = teachingLoad;
    }

    public Iterator getResearchLoad() {
        return researchLoad;
    }

    public void setResearchLoad(Iterator researchLoad) {
        this.researchLoad = researchLoad;
    }

    public Iterator getAdminLoad() {
        return adminLoad;
    }

    public void setAdminLoad(Iterator adminLoad) {
        this.adminLoad = adminLoad;
    }

    public double getTeachingUnits() {
        return teachingUnits;
    }

    public void setTeachingUnits(double teachingUnits) {
        this.teachingUnits = teachingUnits;
    }

    public double getResearchUnits() {
        return researchUnits;
    }

    public void setResearchUnits(double researchUnits) {
        this.researchUnits = researchUnits;
    }

    public double getAdminUnits() {
        return adminUnits;
    }

    public void setAdminUnits(double adminUnits) {
        this.adminUnits = adminUnits;
    }

    public double getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(double totalUnits) {
        this.totalUnits = totalUnits;
    }

    public double getNumPreparations() {
        return numPreparations;
    }

    public void setNumPreparations(double numPreparations) {
        this.numPreparations = numPreparations;
    }
}
