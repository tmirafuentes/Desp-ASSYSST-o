package org.dlsu.arrowsmith.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ElecOffer {
    private long elecOffer_id;
    private long offering_id;
    private long courseEquivalence_id;

    public ElecOffer() {
    }

    public ElecOffer(long elecOffer_id, long offering_id, long courseEquivalence_id) {
        this.elecOffer_id = elecOffer_id;
        this.offering_id = offering_id;
        this.courseEquivalence_id = courseEquivalence_id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getElecOffer_id() {
        return elecOffer_id;
    }

    public void setElecOffer_id(long elecOffer_id) {
        this.elecOffer_id = elecOffer_id;
    }

    public long getOffering_id() {
        return offering_id;
    }

    public void setOffering_id(long offering_id) {
        this.offering_id = offering_id;
    }

    public long getCourseEquivalence_id() {
        return courseEquivalence_id;
    }

    public void setCourseEquivalence_id(long courseEquivalence_id) {
        this.courseEquivalence_id = courseEquivalence_id;
    }
}
