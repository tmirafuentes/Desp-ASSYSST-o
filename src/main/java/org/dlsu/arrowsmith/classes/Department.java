package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Department {
    private Long dept_id;
    private String dept_name;
    private String dept_code;
    private College college;
    private Constraints constraints;
    private Set<Department> departments;
    private Set<Deloading> deloadings;
    private Set<FacultyLoad> facultyLoads;
    private Set<User> facultySet;

    public Department() {
    }

    public Department(Long dept_id, String dept_name, String dept_code) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.dept_code = dept_code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDept_id() {
        return dept_id;
    }

    public void setDept_id(Long dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    @ManyToOne
    @JoinColumn(name = "college_id")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @OneToOne(mappedBy = "department")
    public Constraints getConstraints() {
        return constraints;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Deloading> getDeloadings() {
        return deloadings;
    }

    public void setDeloadings(Set<Deloading> deloadings) {
        this.deloadings = deloadings;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<FacultyLoad> getFacultyLoads() {
        return facultyLoads;
    }

    public void setFacultyLoads(Set<FacultyLoad> facultyLoads) {
        this.facultyLoads = facultyLoads;
    }

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<User> getFacultySet() {
        return facultySet;
    }

    public void setFacultySet(Set<User> facultySet) {
        this.facultySet = facultySet;
    }
}
