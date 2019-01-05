package org.dlsu.arrowsmith.classes;

import org.dlsu.arrowsmith.models.Faculty;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Audited
public class CourseOffering {
    private Long offering_id;
    private String section;
    private int term;
    private int start_AY;
    private int end_AY;
    private int max_enrolled;
    private String status;
    private Course course;
    private User faculty;
    private Set<Days> daysSet;

    public CourseOffering() {
    }

    public CourseOffering(Long offering_id, String section, int term, int start_AY, int end_AY, int max_enrolled, String status) {
        this.offering_id = offering_id;
        this.section = section;
        this.term = term;
        this.start_AY = start_AY;
        this.end_AY = end_AY;
        this.max_enrolled = max_enrolled;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getOffering_id() {
        return offering_id;
    }

    public void setOffering_id(Long offering_id) {
        this.offering_id = offering_id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getStart_AY() {
        return start_AY;
    }

    public void setStart_AY(int start_AY) {
        this.start_AY = start_AY;
    }

    public int getEnd_AY() {
        return end_AY;
    }

    public void setEnd_AY(int end_AY) {
        this.end_AY = end_AY;
    }

    public int getMax_enrolled() {
        return max_enrolled;
    }

    public void setMax_enrolled(int max_enrolled) {
        this.max_enrolled = max_enrolled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getFaculty() {
        return faculty;
    }

    public void setFaculty(User faculty) {
        this.faculty = faculty;
    }

    @OneToMany(mappedBy = "CourseOffering", cascade = CascadeType.ALL)
    public Set<Days> getDaysSet() {
        return daysSet;
    }

    public void setDaysSet(Set<Days> daysSet) {
        this.daysSet = daysSet;
    }
}
