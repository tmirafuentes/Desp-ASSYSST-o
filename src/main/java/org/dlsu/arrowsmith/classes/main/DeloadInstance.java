package org.dlsu.arrowsmith.classes.main;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class DeloadInstance {
    private Long deloadInId;
    private int startAY;
    private int endAY;
    private int term;
    private String remarks;
    private Deloading deloading;
    private User faculty;

    public DeloadInstance() {
    }

    public DeloadInstance(Long deloadInId, int startAY, int endAY, int term, String remarks) {
        this.deloadInId = deloadInId;
        this.startAY = startAY;
        this.endAY = endAY;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne
    @JoinColumn(name = "deloadId")
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
