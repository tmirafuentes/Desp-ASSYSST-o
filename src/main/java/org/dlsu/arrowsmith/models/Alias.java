package org.dlsu.arrowsmith.models;

import javax.persistence.*;

@Entity
public class Alias {
    private User user;
    private String alias_name;

    public Alias() {
    }

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAlias_name() {
        return alias_name;
    }

    public void setAlias_name(String alias_name) {
        this.alias_name = alias_name;
    }
}
