package org.dlsu.arrowsmith.revisionHistory;

import org.dlsu.arrowsmith.classes.main.Term;
import org.dlsu.arrowsmith.services.UserService;
import org.hibernate.envers.Audited;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class EntityRevisionTrackerListener implements EntityTrackingRevisionListener
{
    @Autowired
    private UserService userService;

    @Override
    public void entityChanged(Class entityClass, String s,
                              Serializable serializable,
                              RevisionType revisionType, Object revisionEntity)
    {
        System.out.println("String s: " + s + " Serial: " + serializable.toString());

        String entityName = entityClass.getName().replace("org.dlsu.arrowsmith.classes.main.", "");
        ((AuditedRevisionEntity)revisionEntity).addModifiedEntityType(entityName, serializable);
    }

    @Override
    public void newRevision(Object revisionEntity) {
        AuditedRevisionEntity revEntity = (AuditedRevisionEntity) revisionEntity;
        String username = null;

        try {
            if (SecurityContextHolder.getContext().getAuthentication() != null)
            {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal == null)
                    username = "System";
                else if (principal instanceof User)
                    username = ((User) principal).getUsername();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        revEntity.setUserID(username);
        revEntity.setDateModified(new Date());
    }
}
