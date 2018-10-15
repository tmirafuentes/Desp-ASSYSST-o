package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class Preference {
    private long preference_id;
    private String hours_to_teach;
    private String days_to_teach;
    private String courses_to_teach;
    private String other_remarks;
    private Faculty faculty;

    public Preference() {
    }

    public Preference(long preference_id, Faculty faculty, String hours_to_teach, String days_to_teach, String courses_to_teach, String other_remarks) {
        this.preference_id = preference_id;
        this.faculty = faculty;
        this.hours_to_teach = hours_to_teach;
        this.days_to_teach = days_to_teach;
        this.courses_to_teach = courses_to_teach;
        this.other_remarks = other_remarks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getPreference_id() {
        return preference_id;
    }

    public void setPreference_id(long preference_id) {
        this.preference_id = preference_id;
    }

    public String getHours_to_teach() {
        return hours_to_teach;
    }

    public void setHours_to_teach(String hours_to_teach) {
        this.hours_to_teach = hours_to_teach;
    }

    public String getDays_to_teach() {
        return days_to_teach;
    }

    public void setDays_to_teach(String days_to_teach) {
        this.days_to_teach = days_to_teach;
    }

    public String getCourses_to_teach() {
        return courses_to_teach;
    }

    public void setCourses_to_teach(String courses_to_teach) {
        this.courses_to_teach = courses_to_teach;
    }

    public String getOther_remarks() {
        return other_remarks;
    }

    public void setOther_remarks(String other_remarks) {
        this.other_remarks = other_remarks;
    }

    @OneToOne(mappedBy = "preference")
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
