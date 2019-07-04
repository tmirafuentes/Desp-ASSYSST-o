package org.dlsu.arrowsmith.classes.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Term
{
    private Long termID;
    private int startAY;
    private int endAY;
    private int term;
    private boolean currTerm;
    private Set<CourseOffering> courseOfferings;
    private Set<FacultyLoad> facultyLoads;
    private Set<DeloadInstance> deloadInstances;
    private Set<AuditedRevisionEntity> auditedRevisionEntities;

    public Term() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getTermID() {
        return termID;
    }

    public void setTermID(Long termID) {
        this.termID = termID;
    }

    public int getStartAY() {
        return startAY;
    }

    public void setStartAY(int startAY) {
        this.startAY = startAY;
    }

    public int getEndAY() {
        return endAY;
    }

    public void setEndAY(int endAY) {
        this.endAY = endAY;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public boolean isCurrTerm() {
        return currTerm;
    }

    public void setCurrTerm(boolean currTerm) {
        this.currTerm = currTerm;
    }

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void setCourseOfferings(Set<CourseOffering> courseOfferings) {
        this.courseOfferings = courseOfferings;
    }

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<FacultyLoad> getFacultyLoads() {
        return facultyLoads;
    }

    public void setFacultyLoads(Set<FacultyLoad> facultyLoads) {
        this.facultyLoads = facultyLoads;
    }

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<DeloadInstance> getDeloadInstances() {
        return deloadInstances;
    }

    public void setDeloadInstances(Set<DeloadInstance> deloadInstances) {
        this.deloadInstances = deloadInstances;
    }
}
