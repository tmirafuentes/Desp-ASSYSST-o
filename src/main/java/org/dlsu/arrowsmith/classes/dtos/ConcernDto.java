package org.dlsu.arrowsmith.classes.dtos;

import org.dlsu.arrowsmith.classes.main.User;

public class ConcernDto {
    private Long concernId;
    private Long userId;
    private String message;
    private Long sendUserId;
    private String senderLastName;
    private String senderFirstName;

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }


    public Long getConcernId() {
        return concernId;
    }

    public void setConcernId(Long concernId) {
        this.concernId = concernId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSendUserId() {
        return sendUserId;
    }
    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }



    @java.lang.Override
    public java.lang.String toString() {
        return "FacultyLoadModifyDto{" +
                "Concern ID =" + concernId +
                ", Concern Message =" + message +
                '}';
    }
}
