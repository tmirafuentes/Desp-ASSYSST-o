package org.dlsu.arrowsmith.revisionHistory;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Date;

public class EntityRevisionTrackerListener implements EntityTrackingRevisionListener
{
    @Override
    public void entityChanged(Class entityClass, String s,
                              Serializable serializable,
                              RevisionType revisionType, Object revisionEntity)
    {
        String entityName = entityClass.getName().replace("org.dlsu.arrowsmith.classes.main.", "");
        ((AuditedRevisionEntity)revisionEntity).addModifiedEntityType(entityName);
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

        revEntity.setFullName(username);
        revEntity.setDateModified(new Date());
    }
}
