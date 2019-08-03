package org.dlsu.arrowsmith.classes.main;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
public class Course {
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String courseDesc;
    private double units;
    private double numHours;
    private String roomType;
    private College college;
    private Department department;
    private Set<CourseOffering> courseOfferings;
    private Set<User> facultyPreferences;

    public Course() {
    }

    public Course(Long courseId, String courseCode, String courseName, String courseDesc, double units) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseDesc = courseDesc;
        this.units = units;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Column(length = 1000)
    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public double getNumHours() {
        return numHours;
    }

    public void setNumHours(double numHours) {
        this.numHours = numHours;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @ManyToOne
    @JoinColumn(name = "college_id")
    @JsonIgnore
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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void setCourseOfferings(Set<CourseOffering> courseOfferings) {
        this.courseOfferings = courseOfferings;
    }

    @ManyToMany(mappedBy = "coursePreferences", fetch = FetchType.LAZY)
    public Set<User> getFacultyPreferences() {
        return facultyPreferences;
    }

    public void setFacultyPreferences(Set<User> facultyPreferences) {
        this.facultyPreferences = facultyPreferences;
    }
}
