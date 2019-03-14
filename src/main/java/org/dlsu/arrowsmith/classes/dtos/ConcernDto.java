package org.dlsu.arrowsmith.classes.dtos;

import org.dlsu.arrowsmith.classes.main.User;

public class ConcernDto {
    public Long getConcernId() {
        return concernId;
    }

    public void setConcernId(Long concernId) {
        this.concernId = concernId;
    }

    private Long concernId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;

    @java.lang.Override
    public java.lang.String toString() {
        return "FacultyLoadModifyDto{" +
                "Concern ID =" + concernId +
                ", Concern Message =" + message +
                '}';
    }
}
