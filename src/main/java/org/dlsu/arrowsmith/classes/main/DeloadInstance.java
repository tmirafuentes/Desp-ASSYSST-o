package org.dlsu.arrowsmith.classes.main;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Audited
public class DeloadInstance {
    private Long deloadInId;
    private Term term;
    private String remarks;
    private Deloading deloading;
    private User faculty;

    public DeloadInstance() {
    }

    public DeloadInstance(Long deloadInId, Term term, String remarks) {
        this.deloadInId = deloadInId;
        this.term = term;
        this.remarks = remarks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDeloadInId() {
        return deloadInId;
    }

    public void setDeloadInId(Long deloadInId) {
        this.deloadInId = deloadInId;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne
    @JoinColumn(name = "deloadId")
    @Audited(targetAuditMode = NOT_AUDITED)
    public Deloading getDeloading() {
        return deloading;
    }

    public void setDeloading(Deloading deloading) {
        this.deloading = deloading;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    public User getFaculty() {
        return faculty;
    }

    public void setFaculty(User faculty) {
        this.faculty = faculty;
    }
}
