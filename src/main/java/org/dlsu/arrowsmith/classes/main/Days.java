package org.dlsu.arrowsmith.classes.main;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class Days {
    private Long daysId;
    private char classDay;
    private String beginTime;
    private String endTime;
    private CourseOffering courseOffering;
    private Room room;

    public Days() {
    }

    public Days(Long daysId, char classDay, String beginTime, String endTime) {
        this.daysId = daysId;
        this.classDay = classDay;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getdaysId() {
        return daysId;
    }

    public void setdaysId(Long daysId) {
        this.daysId = daysId;
    }

    public char getclassDay() {
        return classDay;
    }

    public void setclassDay(char classDay) {
        this.classDay = classDay;
    }

    public String getbeginTime() {
        return beginTime.trim();
    }

    public void setbeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getendTime() {
        return endTime.trim();
    }

    public void setendTime(String endTime) {
        this.endTime = endTime;
    }

    @ManyToOne
    @JoinColumn(name = "offering_id")
    public CourseOffering getCourseOffering() {
        return courseOffering;
    }

    public void setCourseOffering(CourseOffering courseOffering) {
        this.courseOffering = courseOffering;
    }

    @ManyToOne
    @JoinColumn(name = "room_id")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
