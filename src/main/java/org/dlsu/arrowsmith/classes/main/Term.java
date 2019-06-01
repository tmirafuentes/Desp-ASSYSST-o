package org.dlsu.arrowsmith.classes.main;

import javax.persistence.Entity;

@Entity
public class Term
{
    private Long termID;
    private int startAY;
    private int endAY;
    private int term;
    private boolean currTerm;

    public Term() {
    }

    public Long getTermID() {
        return termID;
    }

    public void setTermID(Long termID) {
        this.termID = termID;
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

    public boolean isCurrTerm() {
        return currTerm;
    }

    public void setCurrTerm(boolean currTerm) {
        this.currTerm = currTerm;
    }
}
