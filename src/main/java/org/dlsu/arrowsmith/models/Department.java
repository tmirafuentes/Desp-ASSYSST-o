package org.dlsu.arrowsmith.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Department {
    private long department_id;
    private String department_name;
    private String department_code;
    private int department_size;
    private boolean is_obsolete;
    private Set<DegreeProgram> degreePrograms;
    private Set<Load> loads;
    private Set<Deloading> deloadings;
    private Set<Constraints> constraints;
    private Set<User> users;
    private Set<Course> courses;
    private College college;

    public Department() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public int getDepartment_size() {
        return department_size;
    }

    public void setDepartment_size(int department_size) {
        this.department_size = department_size;
    }

    public boolean isIs_obsolete() {
        return is_obsolete;
    }

    public void setIs_obsolete(boolean is_obsolete) {
        this.is_obsolete = is_obsolete;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<DegreeProgram> getDegreePrograms() {
        return degreePrograms;
    }

    public void setDegreePrograms(Set<DegreeProgram> degreePrograms) {
        this.degreePrograms = degreePrograms;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Load> getLoads() {
        return loads;
    }

    public void setLoads(Set<Load> loads) {
        this.loads = loads;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Deloading> getDeloadings() {
        return deloadings;
    }

    public void setDeloadings(Set<Deloading> deloadings) {
        this.deloadings = deloadings;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Constraints> getConstraints() {
        return constraints;
    }

    public void setConstraints(Set<Constraints> constraints) {
        this.constraints = constraints;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @ManyToOne
    @JoinColumn(name = "college_id")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
