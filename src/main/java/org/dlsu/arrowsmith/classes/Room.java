package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

public class Room {
    private Long room_id;
    private String room_type;
    private int room_capacity;
    private Building building;
    private Set<Days> daysSet;

    public Room() {
    }

    public Room(Long room_id, String room_type, int room_capacity) {
        this.room_id = room_id;
        this.room_type = room_type;
        this.room_capacity = room_capacity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(int room_capacity) {
        this.room_capacity = room_capacity;
    }

    @ManyToOne
    @JoinColumn(name = "building_id")
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    public Set<Days> getDaysSet() {
        return daysSet;
    }

    public void setDaysSet(Set<Days> daysSet) {
        this.daysSet = daysSet;
    }
}
