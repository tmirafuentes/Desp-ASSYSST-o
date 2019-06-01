package org.dlsu.arrowsmith.classes.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
//@Audited(targetAuditMode = NOT_AUDITED)
public class College {
    private Long collegeId;
    private String collegeName;
    private String collegeCode;
    private Set<Department> departments;
    private Set<Course> courses;
    private Set<DegreeProgram> degreePrograms;
    private Set<FacultyLoad> facultyLoads;
    private Set<User> users;
    private Set<Deloading> deloadingSet;

    public College() {
    }

    public College(Long collegeId, String collegeName, String collegeCode) {
        this.collegeId = collegeId;
        this.collegeName = collegeName;
        this.collegeCode = collegeCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeCode() {
        return collegeCode;
    }

    public void setCollegeCode(String collegeCode) {
        this.collegeCode = collegeCode;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    @JsonIgnore
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<DegreeProgram> getDegreePrograms() {
        return degreePrograms;
    }

    public void setDegreePrograms(Set<DegreeProgram> degreePrograms) {
        this.degreePrograms = degreePrograms;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<FacultyLoad> getFacultyLoads() {
        return facultyLoads;
    }

    public void setFacultyLoads(Set<FacultyLoad> facultyLoads) {
        this.facultyLoads = facultyLoads;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<Deloading> getDeloadingSet() {
        return deloadingSet;
    }

    public void setDeloadingSet(Set<Deloading> deloadingSet) {
        this.deloadingSet = deloadingSet;
    }
}
