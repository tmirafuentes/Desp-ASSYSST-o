package org.dlsu.arrowsmith.classes;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Deloading {
    private Long deload_id;
    private String deload_name;
    private String deload_code;
    private String deload_type;
    private String description;
    private double units;
    private College college;
    private Department department;
    private Set<DeloadInstance> deloadInstances;

    public Deloading() {
    }

    public Deloading(Long deload_id, String deload_name, String deload_code, String deload_type, String description, double units) {
        this.deload_id = deload_id;
        this.deload_name = deload_name;
        this.deload_code = deload_code;
        this.deload_type = deload_type;
        this.description = description;
        this.units = units;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDeload_id() {
        return deload_id;
    }

    public void setDeload_id(Long deload_id) {
        this.deload_id = deload_id;
    }

    public String getDeload_name() {
        return deload_name;
    }

    public void setDeload_name(String deload_name) {
        this.deload_name = deload_name;
    }

    public String getDeload_code() {
        return deload_code;
    }

    public void setDeload_code(String deload_code) {
        this.deload_code = deload_code;
    }

    public String getDeload_type() {
        return deload_type;
    }

    public void setDeload_type(String deload_type) {
        this.deload_type = deload_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @JoinColumn(name = "department_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy = "deload", cascade = CascadeType.ALL)
    public Set<DeloadInstance> getDeloadInstances() {
        return deloadInstances;
    }

    public void setDeloadInstances(Set<DeloadInstance> deloadInstances) {
        this.deloadInstances = deloadInstances;
    }
}
