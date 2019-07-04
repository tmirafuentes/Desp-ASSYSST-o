package org.dlsu.arrowsmith.classes.dtos.ASSYSTX2;

import java.util.Date;

public class RecentChangesDTO
{
    private String fullName;
    private String position;
    private Date timestamp;
    private String subject;
    private Long revNumber;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getRevNumber() {
        return revNumber;
    }

    public void setRevNumber(Long revNumber) {
        this.revNumber = revNumber;
    }
}
