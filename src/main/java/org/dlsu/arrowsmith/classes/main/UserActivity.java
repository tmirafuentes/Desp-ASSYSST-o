package org.dlsu.arrowsmith.classes.main;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserActivity {
    private Long userId;
    private Long lastConcernSeen;
    private boolean concernNotified;
    private Long lastOfferingModified;
    private boolean offeringNotified;
    private Long lastRevisionSeen;
    private boolean revisionNotified;

    public UserActivity() {
    }

    @Id
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastConcernSeen() {
        return lastConcernSeen;
    }

    public void setLastConcernSeen(Long lastConcernSeen) {
        this.lastConcernSeen = lastConcernSeen;
    }

    public boolean isConcernNotified() {
        return concernNotified;
    }

    public void setConcernNotified(boolean concernNotified) {
        this.concernNotified = concernNotified;
    }

    public Long getLastOfferingModified() {
        return lastOfferingModified;
    }

    public void setLastOfferingModified(Long lastOfferingModified) {
        this.lastOfferingModified = lastOfferingModified;
    }

    public boolean isOfferingNotified() {
        return offeringNotified;
    }

    public void setOfferingNotified(boolean offeringNotified) {
        this.offeringNotified = offeringNotified;
    }

    public Long getLastRevisionSeen() {
        return lastRevisionSeen;
    }

    public void setLastRevisionSeen(Long lastRevisionSeen) {
        this.lastRevisionSeen = lastRevisionSeen;
    }

    public boolean isRevisionNotified() {
        return revisionNotified;
    }

    public void setRevisionNotified(boolean revisionNotified) {
        this.revisionNotified = revisionNotified;
    }
}
