package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    private Long user_id;
    private String first_name;
    private String last_name;
    private String user_type;
    private String password;
    private College college;
    private Set<Course> coursePreferences;
    private Set<DeloadInstance> deloadInstances;
    private Department department;
    private Set<FacultyLoad> facultyLoads;
    private Set<Concern> concernsSent;
    private Set<Concern> concernsReceived;

    public User() {
    }

    public User(Long user_id, String first_name, String last_name, String user_type, String password) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_type = user_type;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "college_id")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<DeloadInstance> getDeloadInstances() {
        return deloadInstances;
    }

    public void setDeloadInstances(Set<DeloadInstance> deloadInstances) {
        this.deloadInstances = deloadInstances;
    }

    @ManyToOne
    @JoinColumn(name = "dept_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<FacultyLoad> getFacultyLoads() {
        return facultyLoads;
    }

    public void setFacultyLoads(Set<FacultyLoad> facultyLoads) {
        this.facultyLoads = facultyLoads;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Concern> getConcernsSent() {
        return concernsSent;
    }

    public void setConcernsSent(Set<Concern> concernsSent) {
        this.concernsSent = concernsSent;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Concern> getConcernsReceived() {
        return concernsReceived;
    }

    public void setConcernsReceived(Set<Concern> concernsReceived) {
        this.concernsReceived = concernsReceived;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "preference",
            joinColumns = @JoinColumn(name = "faculty_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"))
    public Set<Course> getCoursePreferences() {
        return coursePreferences;
    }

    public void setCoursePreferences(Set<Course> coursePreferences) {
        this.coursePreferences = coursePreferences;
    }
}
