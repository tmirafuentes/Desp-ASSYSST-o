package org.dlsu.arrowsmith.classes.main;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserActivity
{
    private Long userId;
    private Long lastConcern;
    private boolean concernNotified;
    private Long lastOffering;
    private boolean offeringNotified;

    public UserActivity() {
    }

    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastConcern() {
        return lastConcern;
    }

    public void setLastConcern(Long lastConcern) {
        this.lastConcern = lastConcern;
    }

    public boolean isConcernNotified() {
        return concernNotified;
    }

    public void setConcernNotified(boolean concernNotified) {
        this.concernNotified = concernNotified;
    }

    public Long getLastOffering() {
        return lastOffering;
    }

    public void setLastOffering(Long lastOffering) {
        this.lastOffering = lastOffering;
    }

    public boolean isOfferingNotified() {
        return offeringNotified;
    }

    public void setOfferingNotified(boolean offeringNotified) {
        this.offeringNotified = offeringNotified;
    }
}
