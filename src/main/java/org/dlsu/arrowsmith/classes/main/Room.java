package org.dlsu.arrowsmith.classes.main;

import org.dlsu.arrowsmith.classes.main.Building;
import org.dlsu.arrowsmith.classes.main.Days;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Audited(targetAuditMode = NOT_AUDITED)
public class Room {
    private Long roomId;
    private String roomType;
    private String roomCode;
    private int roomCapacity;
    private Building building;
    private Set<Days> daysSet;

    public Room() {
    }

    public Room(Long roomId, String roomType, int roomCapacity) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomCapacity = roomCapacity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    @ManyToOne
    @JoinColumn(name = "buildingId")
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
