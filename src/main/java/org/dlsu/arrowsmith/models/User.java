package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class User {
    private long user_id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String user_type;
    private String password;
    private Alias alias;
    private College college;
    private Department department;

    public User() {
    }

    @Id
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
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
    @JoinColumn(name = "department_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
