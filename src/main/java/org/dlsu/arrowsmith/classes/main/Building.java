package org.dlsu.arrowsmith.classes.main;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Building {
    private Long bldgId;
    private String bldgName;
    private String bldgCode;
    private String campus;
    private Set<Room> rooms;

    public Building() { }

    public Building(Long bldgId, String bldgName, String bldgCode, String campus, Set<Room> rooms) {
        this.bldgId = bldgId;
        this.bldgName = bldgName;
        this.bldgCode = bldgCode;
        this.campus = campus;
        this.rooms = rooms;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getBldgId() {
        return bldgId;
    }

    public void setBldgId(Long bldgId) {
        this.bldgId = bldgId;
    }

    public String getBldgName() {
        return bldgName;
    }

    public void setBldgName(String bldgName) {
        this.bldgName = bldgName;
    }

    public String getBldgCode() {
        return bldgCode;
    }

    public void setBldgCode(String bldgCode) {
        this.bldgCode = bldgCode;
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
