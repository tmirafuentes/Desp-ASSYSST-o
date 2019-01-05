package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class College {
    private Long college_id;
    private String college_name;
    private String college_code;
    private Set<Department> departments;
    private Set<Course> courses;
    private Set<DegreeProgram> degreePrograms;
    private Set<FacultyLoad> facultyLoads;
    private Set<User> users;
    private Set<Deloading> deloadingSet;

    public College() {
    }

    public College(Long college_id, String college_name, String college_code) {
        this.college_id = college_id;
        this.college_name = college_name;
        this.college_code = college_code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getCollege_id() {
        return college_id;
    }

    public void setCollege_id(Long college_id) {
        this.college_id = college_id;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getCollege_code() {
        return college_code;
    }

    public void setCollege_code(String college_code) {
        this.college_code = college_code;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
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
