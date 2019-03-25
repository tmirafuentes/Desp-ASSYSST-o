package org.dlsu.arrowsmith.classes.dtos;

import org.dlsu.arrowsmith.classes.main.User;

public class ConcernDto {
    private Long userId;
    private String message;
    private String senderName;

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String date) {
        this.dateAdded = date;
    }

    private String dateAdded;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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


    @java.lang.Override
    public java.lang.String toString() {
        return "FacultyLoadModifyDto{" +
                ", Concern Message =" + message +
                '}';
    }
}
