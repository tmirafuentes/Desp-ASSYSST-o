package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class Flowcourse {
    private long flowCourse_id;
    private int term;
    private Flowchart flowchart;
    private Course course;

    public Flowcourse() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getFlowCourse_id() {
        return flowCourse_id;
    }

    public void setFlowCourse_id(long flowCourse_id) {
        this.flowCourse_id = flowCourse_id;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    @ManyToOne
    @JoinColumn(name = "flowchart_id")
    public Flowchart getFlowchart() {
        return flowchart;
    }

    public void setFlowchart(Flowchart flowchart) {
        this.flowchart = flowchart;
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
