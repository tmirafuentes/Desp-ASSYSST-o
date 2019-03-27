package org.dlsu.arrowsmith.revisionHistory;

import org.dlsu.arrowsmith.security.SecurityService;
import org.dlsu.arrowsmith.services.UserService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

public class AuditingRevisionListener implements RevisionListener {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Override
    public void newRevision(Object revisionEntity) {
    }
}
