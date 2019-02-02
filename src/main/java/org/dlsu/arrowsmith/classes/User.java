package org.dlsu.arrowsmith.classes;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Audited(targetAuditMode = NOT_AUDITED)
public class User {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String userType;
    private String password;
    private College college;
    private Set<Course> coursePreferences;
    private Set<CourseOffering> teachingLoads;
    private Set<DeloadInstance> deloadInstances;
    private Department department;
    private Set<FacultyLoad> facultyLoads;
    private Set<Concern> concernsSent;
    private Set<Concern> concernsReceived;
    private Set<Role> roles;

    public User() {
    }

    public User(Long userId, String firstName, String lastName, String userType, String password) {
        this.userId = userId;
        this.username = String.valueOf(userId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.password = password;
    }

    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.username = String.valueOf(userId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToOne
    @JoinColumn(name = "collegeId")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    public Set<DeloadInstance> getDeloadInstances() {
        return deloadInstances;
    }

    public void setDeloadInstances(Set<DeloadInstance> deloadInstances) {
        this.deloadInstances = deloadInstances;
    }

    @ManyToOne
    @JoinColumn(name = "deptId")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    public Set<FacultyLoad> getFacultyLoads() {
        return facultyLoads;
    }

    public void setFacultyLoads(Set<FacultyLoad> facultyLoads) {
        this.facultyLoads = facultyLoads;
    }

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    public Set<Concern> getConcernsSent() {
        return concernsSent;
    }

    public void setConcernsSent(Set<Concern> concernsSent) {
        this.concernsSent = concernsSent;
    }

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    public Set<Concern> getConcernsReceived() {
        return concernsReceived;
    }

    public void setConcernsReceived(Set<Concern> concernsReceived) {
        this.concernsReceived = concernsReceived;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "preference",
            joinColumns = @JoinColumn(name = "facultyId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "courseId", referencedColumnName = "courseId"))
    public Set<Course> getCoursePreferences() {
        return coursePreferences;
    }

    public void setCoursePreferences(Set<Course> coursePreferences) {
        this.coursePreferences = coursePreferences;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    public Set<CourseOffering> getTeachingLoads() {
        return teachingLoads;
    }

    public void setTeachingLoads(Set<CourseOffering> teachingLoads) {
        this.teachingLoads = teachingLoads;
    }
}
