package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Building {
    private Long bldg_id;
    private String bldg_name;
    private String bldg_code;
    private String campus;
    private Set<Room> rooms;

    public Building() { }

    public Building(Long bldg_id, String bldg_name, String bldg_code, String campus) {
        this.bldg_id = bldg_id;
        this.bldg_name = bldg_name;
        this.bldg_code = bldg_code;
        this.campus = campus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getBldg_id() {
        return bldg_id;
    }

    public void setBldg_id(Long bldg_id) {
        this.bldg_id = bldg_id;
    }

    public String getBldg_name() {
        return bldg_name;
    }

    public void setBldg_name(String bldg_name) {
        this.bldg_name = bldg_name;
    }

    public String getBldg_code() {
        return bldg_code;
    }

    public void setBldg_code(String bldg_code) {
        this.bldg_code = bldg_code;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
}
