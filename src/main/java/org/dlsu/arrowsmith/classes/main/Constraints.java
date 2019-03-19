package org.dlsu.arrowsmith.classes.main;

import javax.persistence.*;

@Entity
public class Constraints {
    private Long constraint_id;
    private int MAX_HOURS_PER_DAY;
    private int MAX_CONSECUTIVE_HOURS;
    private int MAX_FULLTIME_LOAD;
    private int MIN_FULLTIME_LOAD;
    private int MAX_PARTTIME_LOAD;
    private int MIN_PARTTIME_LOAD;
    private Department department;

    public Constraints() {
    }

    public Constraints(Long constraint_id, int MAX_HOURS_PER_DAY, int MAX_CONSECUTIVE_HOURS, int MAX_FULLTIME_LOAD, int MIN_FULLTIME_LOAD, int MAX_PARTTIME_LOAD, int MIN_PARTTIME_LOAD) {
        this.constraint_id = constraint_id;
        this.MAX_HOURS_PER_DAY = MAX_HOURS_PER_DAY;
        this.MAX_CONSECUTIVE_HOURS = MAX_CONSECUTIVE_HOURS;
        this.MAX_FULLTIME_LOAD = MAX_FULLTIME_LOAD;
        this.MIN_FULLTIME_LOAD = MIN_FULLTIME_LOAD;
        this.MAX_PARTTIME_LOAD = MAX_PARTTIME_LOAD;
        this.MIN_PARTTIME_LOAD = MIN_PARTTIME_LOAD;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getConstraint_id() {
        return constraint_id;
    }

    public void setConstraint_id(Long constraint_id) {
        this.constraint_id = constraint_id;
    }

    public int getMAX_HOURS_PER_DAY() {
        return MAX_HOURS_PER_DAY;
    }

    public void setMAX_HOURS_PER_DAY(int MAX_HOURS_PER_DAY) {
        this.MAX_HOURS_PER_DAY = MAX_HOURS_PER_DAY;
    }

    public int getMAX_CONSECUTIVE_HOURS() {
        return MAX_CONSECUTIVE_HOURS;
    }

    public void setMAX_CONSECUTIVE_HOURS(int MAX_CONSECUTIVE_HOURS) {
        this.MAX_CONSECUTIVE_HOURS = MAX_CONSECUTIVE_HOURS;
    }

    public int getMAX_FULLTIME_LOAD() {
        return MAX_FULLTIME_LOAD;
    }

    public void setMAX_FULLTIME_LOAD(int MAX_FULLTIME_LOAD) {
        this.MAX_FULLTIME_LOAD = MAX_FULLTIME_LOAD;
    }

    public int getMIN_FULLTIME_LOAD() {
        return MIN_FULLTIME_LOAD;
    }

    public void setMIN_FULLTIME_LOAD(int MIN_FULLTIME_LOAD) {
        this.MIN_FULLTIME_LOAD = MIN_FULLTIME_LOAD;
    }

    public int getMAX_PARTTIME_LOAD() {
        return MAX_PARTTIME_LOAD;
    }

    public void setMAX_PARTTIME_LOAD(int MAX_PARTTIME_LOAD) {
        this.MAX_PARTTIME_LOAD = MAX_PARTTIME_LOAD;
    }

    public int getMIN_PARTTIME_LOAD() {
        return MIN_PARTTIME_LOAD;
    }

    public void setMIN_PARTTIME_LOAD(int MIN_PARTTIME_LOAD) {
        this.MIN_PARTTIME_LOAD = MIN_PARTTIME_LOAD;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
