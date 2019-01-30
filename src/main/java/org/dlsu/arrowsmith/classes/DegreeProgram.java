package org.dlsu.arrowsmith.classes;

import org.hibernate.envers.Audited;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Audited(targetAuditMode = NOT_AUDITED)
public class DegreeProgram {
    private Long degreeId;
    private String degreeName;
    private String degreeCode;
    private College college;
    private Department department;

    public DegreeProgram() {
    }

    public DegreeProgram(Long degreeId, String degreeName, String degreeCode) {
        this.degreeId = degreeId;
        this.degreeName = degreeName;
        this.degreeCode = degreeCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Long degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getDegreeCode() {
        return degreeCode;
    }

    public void setDegreeCode(String degreeCode) {
        this.degreeCode = degreeCode;
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
    @JoinColumn(name = "deptId")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
