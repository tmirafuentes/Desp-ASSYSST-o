package org.dlsu.arrowsmith.classes;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
public class DeloadInstance {
    private Long deloadIn_id;
    private int start_AY;
    private int end_AY;
    private int term;
    private String remarks;
    private Deloading deloading;
    private User faculty;

    public DeloadInstance() {
    }

    public DeloadInstance(Long deloadIn_id, int start_AY, int end_AY, int term, String remarks) {
        this.deloadIn_id = deloadIn_id;
        this.start_AY = start_AY;
        this.end_AY = end_AY;
        this.term = term;
        this.remarks = remarks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getDeloadIn_id() {
        return deloadIn_id;
    }

    public void setDeloadIn_id(Long deloadIn_id) {
        this.deloadIn_id = deloadIn_id;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne
    @JoinColumn(name = "deload_id")
    public Deloading getDeloading() {
        return deloading;
    }

    public void setDeloading(Deloading deloading) {
        this.deloading = deloading;
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
