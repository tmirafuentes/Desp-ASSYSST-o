package org.dlsu.arrowsmith.classes.main;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
public class Deloading {
    private Long deloadId;
    private String deloadName;
    private String deloadCode;
    private String deloadType;
    private String description;
    private double units;
    private College college;
    private Department department;
    private Set<DeloadInstance> deloadInstances;

    public Deloading() {
    }

    public Deloading(Long deloadId, String deloadName, String deloadCode, String deloadType, String description, double units) {
        this.deloadId = deloadId;
        this.deloadName = deloadName;
        this.deloadCode = deloadCode;
        this.deloadType = deloadType;
        this.description = description;
        this.units = units;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDeloadId() {
        return deloadId;
    }

    public void setDeloadId(Long deloadId) {
        this.deloadId = deloadId;
    }

    public String getDeloadName() {
        return deloadName;
    }

    public void setDeloadName(String deloadName) {
        this.deloadName = deloadName;
    }

    public String getDeloadCode() {
        return deloadCode;
    }

    public void setDeloadCode(String deloadCode) {
        this.deloadCode = deloadCode;
    }

    public String getDeloadType() {
        return deloadType;
    }

    public void setDeloadType(String deloadType) {
        this.deloadType = deloadType;
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
    @JoinColumn(name = "collegeId")
    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @ManyToOne
    @JoinColumn(name = "departmentId")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @OneToMany(mappedBy = "deloading", cascade = CascadeType.ALL)
    public Set<DeloadInstance> getDeloadInstances() {
        return deloadInstances;
    }

    public void setDeloadInstances(Set<DeloadInstance> deloadInstances) {
        this.deloadInstances = deloadInstances;
    }
}
