package org.dlsu.arrowsmith.classes.main;

import org.hibernate.envers.Audited;

import javax.persistence.*;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
@Audited(targetAuditMode = NOT_AUDITED)
public class Concern {
    private Long concernId;
    private String message;
    private boolean acknowledged;
    private User sender;
    private User receiver;

    public Concern() {
    }

    public Concern(Long concernId, String message, boolean acknowledged) {
        this.concernId = concernId;
        this.message = message;
        this.acknowledged = acknowledged;
    }

    public Concern(Long concernId, String message, boolean acknowledged, User sender, User receiver) {
        this.concernId = concernId;
        this.message = message;
        this.acknowledged = acknowledged;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getconcernId() {
        return concernId;
    }

    public void setconcernId(Long concernId) {
        this.concernId = concernId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    @ManyToOne
    @JoinColumn(name = "sender")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver")//, insertable=false, updatable=false)
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
