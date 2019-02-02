package org.dlsu.arrowsmith.classes;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

@Entity
@Audited
public class FacultyLoad {
    private Long loadId;
    private int startAY;
    private int endAY;
    private int term;
    private double adminLoad;
    private double researchLoad;
    private double teachingLoad;
    private double nonacadLoad;
    private double deloadedLoad;
    private double totalLoad;
    private boolean onLeave;
    private String leaveType;
    private College college;
    private Department department;
    private User faculty;

    public FacultyLoad() {
    }

    public FacultyLoad(Long loadId, int startAY, int endAY, int term, double adminLoad, double researchLoad, double teachingLoad, double nonacadLoad, double deloadedLoad, double totalLoad, boolean onLeave, String leaveType) {
        this.loadId = loadId;
        this.startAY = startAY;
        this.endAY = endAY;
        this.term = term;
        this.adminLoad = adminLoad;
        this.researchLoad = researchLoad;
        this.teachingLoad = teachingLoad;
        this.nonacadLoad = nonacadLoad;
        this.deloadedLoad = deloadedLoad;
        this.totalLoad = totalLoad;
        this.onLeave = onLeave;
        this.leaveType = leaveType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getloadId() {
        return loadId;
    }

    public void setloadId(Long loadId) {
        this.loadId = loadId;
    }

    public int getStartAY() {
        return startAY;
    }

    public void setStartAY(int startAY) {
        this.startAY = startAY;
    }

    public int getEndAY() {
        return endAY;
    }

    public void setEndAY(int endAY) {
        this.endAY = endAY;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
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

    public double getDeloadedLoad() {
        return deloadedLoad;
    }

    public void setDeloadedLoad(double deloadedLoad) {
        this.deloadedLoad = deloadedLoad;
    }

    public double getTotalLoad() {
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
