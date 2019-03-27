package org.dlsu.arrowsmith.classes.dtos;

import java.util.Date;

public class RevHistoryLinkDto {
    private String fullname;
    private String position;
    private Date timestamp;
    private Long revNumber;

    public RevHistoryLinkDto() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getRevNumber() {
        return revNumber;
    }

    public void setRevNumber(Long revNumber) {
        this.revNumber = revNumber;
    }
}
