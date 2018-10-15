package org.dlsu.arrowsmith.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equivalence {
    private long equivalence_id;
    private long course_id;
    private long courseEquivalence_id;

    public Equivalence() {
    }

    public Equivalence(long equivalence_id, long course_id, long courseEquivalence_id) {
        this.equivalence_id = equivalence_id;
        this.course_id = course_id;
        this.courseEquivalence_id = courseEquivalence_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getEquivalence_id() {
        return equivalence_id;
    }

    public void setEquivalence_id(long equivalence_id) {
        this.equivalence_id = equivalence_id;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public long getCourseEquivalence_id() {
        return courseEquivalence_id;
    }

    public void setCourseEquivalence_id(long courseEquivalence_id) {
        this.courseEquivalence_id = courseEquivalence_id;
    }
}
