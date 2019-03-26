package org.dlsu.arrowsmith.classes.dtos;

import java.util.Date;

public class RevHistoryLinkDto {
    private String fullname;
    private Date timestamp;

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
}
