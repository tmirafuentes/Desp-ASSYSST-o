package org.dlsu.arrowsmith.classes.dtos;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;

public class FacultyLoadModifyDto {
    private Long loadId;
    private int startAY;
    private int endAY;
    private int term;
    private double adminLoad;
    private double researchLoad;
    private double teachingLoad;
    private double deloadedLoad;
    private double totalLoad;

    public Long getLoadId() {
        return loadId;
    }

    public void setLoadId(Long loadId) {
        this.loadId = loadId;
    }

    public int getStartAY() {
        return startAY;
    }

    public void setStartAY(int startAY) {
        this.startAY = startAY;
    }

    public int getEndAY() {
        return endAY;
    }

    public void setEndAY(int endAY) {
        this.endAY = endAY;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

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

    public double getDeloadedLoad() {
        return deloadedLoad;
    }

    public void setDeloadedLoad(double deloadedLoad) {
        this.deloadedLoad = deloadedLoad;
    }

    public double getTotalLoad() {
        return totalLoad;
    }

    public void setTotalLoad(double totalLoad) {
        this.totalLoad = totalLoad;
    }



    @java.lang.Override
    public java.lang.String toString() {
        return "FacultyLoadModifyDto{" +
                "adminLoad=" + adminLoad +
                ", researchLoad='" + researchLoad + '\'' +
                ", teachingLoad='" + teachingLoad + '\'' +
                ", deloadedLoad='" + deloadedLoad + '\'' +
                ", totalLoad=" + totalLoad +
                ", load ID =" + loadId +
                '}';
    }

}
