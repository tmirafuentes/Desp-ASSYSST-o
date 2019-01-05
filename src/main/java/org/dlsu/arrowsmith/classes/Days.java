package org.dlsu.arrowsmith.classes;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class Days {
    private Long days_id;
    private char class_day;
    private String begin_time;
    private String end_time;
    private CourseOffering courseOffering;
    private Room room;

    public Days() {
    }

    public Days(Long days_id, char class_day, String begin_time, String end_time) {
        this.days_id = days_id;
        this.class_day = class_day;
        this.begin_time = begin_time;
        this.end_time = end_time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDays_id() {
        return days_id;
    }

    public void setDays_id(Long days_id) {
        this.days_id = days_id;
    }

    public char getClass_day() {
        return class_day;
    }

    public void setClass_day(char class_day) {
        this.class_day = class_day;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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
