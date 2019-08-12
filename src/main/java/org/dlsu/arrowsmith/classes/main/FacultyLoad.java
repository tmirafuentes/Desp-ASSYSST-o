package org.dlsu.arrowsmith.classes.main;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

@Entity
@Audited
public class FacultyLoad {
    private Long loadId;
    private double adminLoad;
    private double researchLoad;
    private double teachingLoad;
    private double nonacadLoad;
    private double totalLoad;
    private int preparations;
    private boolean onLeave;
    private String leaveType;
    private Term term;
    private College college;
    private Department department;
    private User faculty;

    public FacultyLoad() {
    }

    public int getPreparations() {
        return preparations;
    }

    public void setPreparations(int preparations) {
        this.preparations = preparations;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getloadId() {
        return loadId;
    }

    public void setloadId(Long loadId) {
        this.loadId = loadId;
    }

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "termID")
    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public double getAdminLoad() {
        return adminLoad;
    }

    public void setAdminLoad(double adminLoad) {
        this.adminLoad = adminLoad;
    }

    public double getResearchLoad() {
        return researchLoad;
    }

    public void setResearchLoad(double researchLoad) {
        this.researchLoad = researchLoad;
    }

    public double getTeachingLoad() {
        return teachingLoad;
    }

    public void setTeachingLoad(double teachingLoad) {
        this.teachingLoad = teachingLoad;
    }

    public double getNonacadLoad() {
        return nonacadLoad;
    }

    public void setNonacadLoad(double nonacadLoad) {
        this.nonacadLoad = nonacadLoad;
    }

    public double getTotalLoad() {
        totalLoad = this.adminLoad + this.nonacadLoad + this.researchLoad + this.teachingLoad;
        return totalLoad;
    }

    public void setTotalLoad(double totalLoad) {
        this.totalLoad = totalLoad;
    }

    @ColumnDefault("false")
    public boolean isOnLeave() {
        return onLeave;
    }

    public void setOnLeave(boolean onLeave) {
        this.onLeave = onLeave;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "college_id")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "dept_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getFaculty() {
        return faculty;
    }

    public void setFaculty(User faculty) {
        this.faculty = faculty;
    }
}
