package org.dlsu.arrowsmith.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Specialization {
    private long spec_id;
    private long faculty_id;
    private long area_id;

    public Specialization() {
    }

    public Specialization(long spec_id, long faculty_id, long area_id) {
        this.spec_id = spec_id;
        this.faculty_id = faculty_id;
        this.area_id = area_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(long spec_id) {
        this.spec_id = spec_id;
    }

    public long getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(long faculty_id) {
        this.faculty_id = faculty_id;
    }

    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }
}
