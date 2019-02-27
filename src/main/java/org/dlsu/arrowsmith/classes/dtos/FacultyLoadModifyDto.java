package org.dlsu.arrowsmith.classes.dtos;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;

public class FacultyLoadModifyDto {
    private Long loadId;
    private String deloadType;
    private int deloadUnits;

    public String getDeloadType() {
        return deloadType;
    }

    public void setDeloadType(String deloadType) {
        this.deloadType = deloadType;
    }

    public int getDeloadUnits() {
        return deloadUnits;
    }

    public void setDeloadUnits(int deloadLoad) {
        this.deloadUnits = deloadLoad;
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
