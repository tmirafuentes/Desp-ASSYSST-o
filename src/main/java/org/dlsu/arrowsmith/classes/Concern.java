package org.dlsu.arrowsmith.classes;

import javax.persistence.*;

@Entity
public class Concern {
    private Long concern_id;
    private String message;
    private boolean acknowledged;
    private User sender;
    private User receiver;

    public Concern() {
    }

    public Concern(Long concern_id, String message, boolean acknowledged) {
        this.concern_id = concern_id;
        this.message = message;
        this.acknowledged = acknowledged;
    }

    public Concern(Long concern_id, String message, boolean acknowledged, User sender, User receiver) {
        this.concern_id = concern_id;
        this.message = message;
        this.acknowledged = acknowledged;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getConcern_id() {
        return concern_id;
    }

    public void setConcern_id(Long concern_id) {
        this.concern_id = concern_id;
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
    @JoinColumn(name = "user_id")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
