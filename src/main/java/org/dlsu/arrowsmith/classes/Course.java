package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Course {
    private Long course_id;
    private String course_code;
    private String course_name;
    private String course_desc;
    private double units;
    private College college;
    private Department department;
    private Set<CourseOffering> courseOfferings;
    private Set<User> facultyPreferences;

    public Course() {
    }

    public Course(Long course_id, String course_code, String course_name, String course_desc, double units) {
        this.course_id = course_id;
        this.course_code = course_code;
        this.course_name = course_name;
        this.course_desc = course_desc;
        this.units = units;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public void setCourse_desc(String course_desc) {
        this.course_desc = course_desc;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    @ManyToOne
    @JoinColumn(name = "college_id")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @ManyToOne
    @JoinColumn(name = "dept_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    public Set<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void setCourseOfferings(Set<CourseOffering> courseOfferings) {
        this.courseOfferings = courseOfferings;
    }

    @ManyToMany(mappedBy = "courses")
    public Set<User> getFacultyPreferences() {
        return facultyPreferences;
    }

    public void setFacultyPreferences(Set<User> facultyPreferences) {
        this.facultyPreferences = facultyPreferences;
    }
}
