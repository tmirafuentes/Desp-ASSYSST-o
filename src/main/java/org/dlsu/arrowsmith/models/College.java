package org.dlsu.arrowsmith.models;

import com.sun.javafx.geom.transform.BaseTransform;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
public class College {
    private long college_id;
    private String college_code;
    private String college_name;
    private boolean isObsolete;
    private Set<Department> departments;
    private Set<DegreeProgram> degreePrograms;
    private Set<User> users;
    private Set<Deloading> deloadings;
    private Set<Load> loads;
    private Set<Course> courses;
    
    public College(){
        
    }

    public College(long college_id, String college_code, String college_name, boolean isObsolete) {
        this.college_id = college_id;
        this.college_code = college_code;
        this.college_name = college_name;
        this.isObsolete = isObsolete;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getCollege_id() {
        return college_id;
    }

    public void setCollege_id(long college_id) {
        this.college_id = college_id;
    }

    public String getCollege_code() {
        return college_code;
    }

    public void setCollege_code(String college_code) {
        this.college_code = college_code;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public boolean isObsolete() {
        return isObsolete;
    }

    public void setObsolete(boolean obsolete) {
        isObsolete = obsolete;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<DegreeProgram> getDegreePrograms() {
        return degreePrograms;
    }

    public void setDegreePrograms(Set<DegreeProgram> degreePrograms) {
        this.degreePrograms = degreePrograms;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<Deloading> getDeloadings() {
        return deloadings;
    }

    public void setDeloadings(Set<Deloading> deloadings) {
        this.deloadings = deloadings;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<Load> getLoads() {
        return loads;
    }

    public void setLoads(Set<Load> loads) {
        this.loads = loads;
    }

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL)
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
