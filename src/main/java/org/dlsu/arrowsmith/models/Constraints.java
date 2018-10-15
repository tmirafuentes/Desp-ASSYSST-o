package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class Constraints {
    private long constraint_id;
    private float MAX_HOURS_PER_DAY;
    private float MAX_CONSECUTIVE_HOURS;
    private float MAX_PREPARATIONS;
    private float MIN_PREPARATIONS;
    private float MAX_LOAD_PARTTIME;
    private float MIN_LOAD_PARTTIME;
    private float MAX_LOAD_HALFTIME;
    private float MIN_LOAD_HALFTIME;
    private float MAX_LOAD_FULLTIME;
    private float MIN_LOAD_FULLTIME;
    private Department department;

    public Constraints() {
    }

    public Constraints(long constraint_id, Department department, float MAX_HOURS_PER_DAY, float MAX_CONSECUTIVE_HOURS, float MAX_PREPARATIONS, float MIN_PREPARATIONS, float MAX_LOAD_PARTTIME, float MIN_LOAD_PARTTIME, float MAX_LOAD_HALFTIME, float MIN_LOAD_HALFTIME, float MAX_LOAD_FULLTIME, float MIN_LOAD_FULLTIME) {
        this.constraint_id = constraint_id;
        this.department = department;
        this.MAX_HOURS_PER_DAY = MAX_HOURS_PER_DAY;
        this.MAX_CONSECUTIVE_HOURS = MAX_CONSECUTIVE_HOURS;
        this.MAX_PREPARATIONS = MAX_PREPARATIONS;
        this.MIN_PREPARATIONS = MIN_PREPARATIONS;
        this.MAX_LOAD_PARTTIME = MAX_LOAD_PARTTIME;
        this.MIN_LOAD_PARTTIME = MIN_LOAD_PARTTIME;
        this.MAX_LOAD_HALFTIME = MAX_LOAD_HALFTIME;
        this.MIN_LOAD_HALFTIME = MIN_LOAD_HALFTIME;
        this.MAX_LOAD_FULLTIME = MAX_LOAD_FULLTIME;
        this.MIN_LOAD_FULLTIME = MIN_LOAD_FULLTIME;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getConstraint_id() {
        return constraint_id;
    }

    public void setConstraint_id(long constraint_id) {
        this.constraint_id = constraint_id;
    }

    @ManyToOne
    @JoinColumn(name = "department_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public float getMAX_HOURS_PER_DAY() {
        return MAX_HOURS_PER_DAY;
    }

    public void setMAX_HOURS_PER_DAY(float MAX_HOURS_PER_DAY) {
        this.MAX_HOURS_PER_DAY = MAX_HOURS_PER_DAY;
    }

    public float getMAX_CONSECUTIVE_HOURS() {
        return MAX_CONSECUTIVE_HOURS;
    }

    public void setMAX_CONSECUTIVE_HOURS(float MAX_CONSECUTIVE_HOURS) {
        this.MAX_CONSECUTIVE_HOURS = MAX_CONSECUTIVE_HOURS;
    }

    public float getMAX_PREPARATIONS() {
        return MAX_PREPARATIONS;
    }

    public void setMAX_PREPARATIONS(float MAX_PREPARATIONS) {
        this.MAX_PREPARATIONS = MAX_PREPARATIONS;
    }

    public float getMIN_PREPARATIONS() {
        return MIN_PREPARATIONS;
    }

    public void setMIN_PREPARATIONS(float MIN_PREPARATIONS) {
        this.MIN_PREPARATIONS = MIN_PREPARATIONS;
    }

    public float getMAX_LOAD_PARTTIME() {
        return MAX_LOAD_PARTTIME;
    }

    public void setMAX_LOAD_PARTTIME(float MAX_LOAD_PARTTIME) {
        this.MAX_LOAD_PARTTIME = MAX_LOAD_PARTTIME;
    }

    public float getMIN_LOAD_PARTTIME() {
        return MIN_LOAD_PARTTIME;
    }

    public void setMIN_LOAD_PARTTIME(float MIN_LOAD_PARTTIME) {
        this.MIN_LOAD_PARTTIME = MIN_LOAD_PARTTIME;
    }

    public float getMAX_LOAD_HALFTIME() {
        return MAX_LOAD_HALFTIME;
    }

    public void setMAX_LOAD_HALFTIME(float MAX_LOAD_HALFTIME) {
        this.MAX_LOAD_HALFTIME = MAX_LOAD_HALFTIME;
    }

    public float getMIN_LOAD_HALFTIME() {
        return MIN_LOAD_HALFTIME;
    }

    public void setMIN_LOAD_HALFTIME(float MIN_LOAD_HALFTIME) {
        this.MIN_LOAD_HALFTIME = MIN_LOAD_HALFTIME;
    }

    public float getMAX_LOAD_FULLTIME() {
        return MAX_LOAD_FULLTIME;
    }

    public void setMAX_LOAD_FULLTIME(float MAX_LOAD_FULLTIME) {
        this.MAX_LOAD_FULLTIME = MAX_LOAD_FULLTIME;
    }

    public float getMIN_LOAD_FULLTIME() {
        return MIN_LOAD_FULLTIME;
    }

    public void setMIN_LOAD_FULLTIME(float MIN_LOAD_FULLTIME) {
        this.MIN_LOAD_FULLTIME = MIN_LOAD_FULLTIME;
    }
}


