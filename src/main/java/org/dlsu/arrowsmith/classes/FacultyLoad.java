package org.dlsu.arrowsmith.classes;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class FacultyLoad {
    private Long load_id;
    private int start_AY;
    private int end_AY;
    private int term;
    private double admin_load;
    private double research_load;
    private double teaching_load;
    private double nonacad_load;
    private double deloaded_load;
    private double total_load;
    private boolean on_leave;
    private String leave_type;
    private College college;
    private Department department;
    private User faculty;

    public FacultyLoad() {
    }

    public FacultyLoad(Long load_id, int start_AY, int end_AY, int term, double admin_load, double research_load, double teaching_load, double nonacad_load, double deloaded_load, double total_load, boolean on_leave, String leave_type) {
        this.load_id = load_id;
        this.start_AY = start_AY;
        this.end_AY = end_AY;
        this.term = term;
        this.admin_load = admin_load;
        this.research_load = research_load;
        this.teaching_load = teaching_load;
        this.nonacad_load = nonacad_load;
        this.deloaded_load = deloaded_load;
        this.total_load = total_load;
        this.on_leave = on_leave;
        this.leave_type = leave_type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getLoad_id() {
        return load_id;
    }

    public void setLoad_id(Long load_id) {
        this.load_id = load_id;
    }

    public int getStart_AY() {
        return start_AY;
    }

    public void setStart_AY(int start_AY) {
        this.start_AY = start_AY;
    }

    public int getEnd_AY() {
        return end_AY;
    }

    public void setEnd_AY(int end_AY) {
        this.end_AY = end_AY;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getAdmin_load() {
        return admin_load;
    }

    public void setAdmin_load(double admin_load) {
        this.admin_load = admin_load;
    }

    public double getResearch_load() {
        return research_load;
    }

    public void setResearch_load(double research_load) {
        this.research_load = research_load;
    }

    public double getTeaching_load() {
        return teaching_load;
    }

    public void setTeaching_load(double teaching_load) {
        this.teaching_load = teaching_load;
    }

    public double getNonacad_load() {
        return nonacad_load;
    }

    public void setNonacad_load(double nonacad_load) {
        this.nonacad_load = nonacad_load;
    }

    public double getDeloaded_load() {
        return deloaded_load;
    }

    public void setDeloaded_load(double deloaded_load) {
        this.deloaded_load = deloaded_load;
    }

    public double getTotal_load() {
        return total_load;
    }

    public void setTotal_load(double total_load) {
        this.total_load = total_load;
    }

    public boolean isOn_leave() {
        return on_leave;
    }

    public void setOn_leave(boolean on_leave) {
        this.on_leave = on_leave;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getFaculty() {
        return faculty;
    }

    public void setFaculty(User faculty) {
        this.faculty = faculty;
    }
}
