package org.dlsu.arrowsmith.classes.main;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Audited
public class CourseOffering {
    private Long offeringId;
    private String section;
    private int maxEnrolled;
    private String type;
    private Term term;
    private Course course;
    private User faculty;
    private Set<Days> daysSet;

    public CourseOffering() {
    }

    public CourseOffering(Long offeringId, String section, Term term, int maxEnrolled, String type) {
        this.offeringId = offeringId;
        this.section = section;
        this.term = term;
        this.maxEnrolled = maxEnrolled;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Nullable
    public Long getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(Long offeringId) {
        this.offeringId = offeringId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getMaxEnrolled() {
        return maxEnrolled;
    }

    public void setMaxEnrolled(int maxEnrolled) {
        this.maxEnrolled = maxEnrolled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "termID")
    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @NotAudited
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
