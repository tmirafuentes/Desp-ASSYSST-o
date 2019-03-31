package org.dlsu.arrowsmith.classes.main;

import org.apache.tomcat.jni.Local;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Entity
public class Concern {
    private Long concernId;
    private String message;
    private boolean acknowledged;
    private User sender;
    private User receiver;

    public LocalDateTime getDateTimeCommitted() {
        return dateTimeCommitted;
    }

    public void setDateTimeCommitted(LocalDateTime dateTimeCommitted) {
        this.dateTimeCommitted = dateTimeCommitted;
    }

    @Basic
    private java.time.LocalDateTime dateTimeCommitted;

    public Concern() {
        this.dateTimeCommitted = LocalDateTime.now();
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
    //public

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
    @Audited(targetAuditMode = NOT_AUDITED)
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver")//, insert    able=false, updatable=false)
    @Audited(targetAuditMode = NOT_AUDITED)
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
