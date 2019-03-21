package org.dlsu.arrowsmith.revisionHistory;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;

import java.io.Serializable;

public class EntityRevisionTrackerListener implements EntityTrackingRevisionListener
{
    @Override
    public void entityChanged(Class entityClass, String s,
                              Serializable serializable,
                              RevisionType revisionType, Object revisionEntity)
    {
        String entityName = entityClass.getName();
        ((AuditedRevisionEntity)revisionEntity).addModifiedEntityType(entityName);
    }

    @Override
    public void newRevision(Object o) {

    }
}
