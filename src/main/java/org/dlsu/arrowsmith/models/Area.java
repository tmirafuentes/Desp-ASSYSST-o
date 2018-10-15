package org.dlsu.arrowsmith.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Area {
    private long area_id;
    private String area_name;

    public Area() {
    }

    public Area(long area_id, String area_name) {
        this.area_id = area_id;
        this.area_name = area_name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getArea_id() {
        return area_id;
    }

    public void setArea_id(long area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
