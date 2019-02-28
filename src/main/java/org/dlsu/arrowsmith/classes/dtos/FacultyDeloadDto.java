package org.dlsu.arrowsmith.classes.dtos;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;

public class FacultyDeloadDto {
    private Long loadId;
    private String deloadType;
    private String deloadCode;
    private double deloadUnits;

    public String getDeloadType() {
        return deloadType;
    }

    public void setDeloadType(String deloadType) {
        this.deloadType = deloadType;
    }

    public double getDeloadUnits() {
        return deloadUnits;
    }

    public void setDeloadUnits(double deloadLoad) {
        this.deloadUnits = deloadLoad;
    }

    public String getDeloadCode() {
        return deloadCode;
    }

    public void setDeloadCode(String deloadCode) {
        this.deloadCode = deloadCode;
    }

    public Long getLoadId() {
        return loadId;
    }

    public void setLoadId(Long loadId) {
        this.loadId = loadId;
    }




    @java.lang.Override
    public java.lang.String toString() {
        return "FacultyLoadModifyDto{" +
                "load ID =" + loadId +
                ", deloadType =" + deloadType +
                ", deloadUnits =" + deloadUnits +
                '}';
    }

}
