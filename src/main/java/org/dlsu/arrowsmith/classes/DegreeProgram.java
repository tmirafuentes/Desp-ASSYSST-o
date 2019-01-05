package org.dlsu.arrowsmith.classes;

import javax.persistence.*;

@Entity
public class DegreeProgram {
    private Long degree_id;
    private String degree_name;
    private String degree_code;
    private College college;
    private Department department;

    public DegreeProgram() {
    }

    public DegreeProgram(Long degree_id, String degree_name, String degree_code) {
        this.degree_id = degree_id;
        this.degree_name = degree_name;
        this.degree_code = degree_code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDegree_id() {
        return degree_id;
    }

    public void setDegree_id(Long degree_id) {
        this.degree_id = degree_id;
    }

    public String getDegree_name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
    }

    public String getDegree_code() {
        return degree_code;
    }

    public void setDegree_code(String degree_code) {
        this.degree_code = degree_code;
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
}
