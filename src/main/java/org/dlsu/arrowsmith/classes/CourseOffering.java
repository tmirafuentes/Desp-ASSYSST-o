package org.dlsu.arrowsmith.classes;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Audited
public class CourseOffering {
    private Long offeringId;
    private String section;
    private int term;
    private int startAY;
    private int endAY;
    private int maxEnrolled;
    private String status;
    private Course course;
    private User faculty;
    private Set<Days> daysSet;

    public CourseOffering() {
    }

    public CourseOffering(Long offeringId, String section, int term, int startAY, int endAY, int maxEnrolled, String status) {
        this.offeringId = offeringId;
        this.section = section;
        this.term = term;
        this.startAY = startAY;
        this.endAY = endAY;
        this.maxEnrolled = maxEnrolled;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getofferingId() {
        return offeringId;
    }

    public void setofferingId(Long offeringId) {
        this.offeringId = offeringId;
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

    public int getStartAY() {
        return startAY;
    }

    public void setStartAY(int startAY) {
        this.startAY = startAY;
    }

    public int getendAY() {
        return endAY;
    }

    public void setendAY(int endAY) {
        this.endAY = endAY;
    }

    public int getmaxEnrolled() {
        return maxEnrolled;
    }

    public void setmaxEnrolled(int maxEnrolled) {
        this.maxEnrolled = maxEnrolled;
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

    @OneToMany(mappedBy = "courseOffering", cascade = CascadeType.ALL)
    public Set<Days> getDaysSet() {
        return daysSet;
    }

    public void setDaysSet(Set<Days> daysSet) {
        this.daysSet = daysSet;
    }
}
