package org.dlsu.arrowsmith.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Requisite {
    private long requisite_id;
    private long course_id;
    private long req_course_id;
    private int req_type;

    public Requisite() {
    }

    public Requisite(long requisite_id, long course_id, long req_course_id, int req_type) {
        this.requisite_id = requisite_id;
        this.course_id = course_id;
        this.req_course_id = req_course_id;
        this.req_type = req_type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getRequisite_id() {
        return requisite_id;
    }

    public void setRequisite_id(long requisite_id) {
        this.requisite_id = requisite_id;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public long getReq_course_id() {
        return req_course_id;
    }

    public void setReq_course_id(long req_course_id) {
        this.req_course_id = req_course_id;
    }

    public int getReq_type() {
        return req_type;
    }

    public void setReq_type(int req_type) {
        this.req_type = req_type;
    }
}
