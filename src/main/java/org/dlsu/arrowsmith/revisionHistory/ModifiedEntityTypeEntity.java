package org.dlsu.arrowsmith.revisionHistory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ModifiedEntityTypeEntity
{
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private AuditedRevisionEntity revision;

    private String entityClassName;

    private Long entityID;

    public ModifiedEntityTypeEntity() {
    }

    public ModifiedEntityTypeEntity(AuditedRevisionEntity revision, String entityClassName, Long entityID)
    {
        this.revision = revision;
        this.entityClassName = entityClassName;
        this.entityID = entityID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuditedRevisionEntity getRevision() {
        return revision;
    }

    public void setRevision(AuditedRevisionEntity revision) {
        this.revision = revision;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public Long getEntityID() {
        return entityID;
    }

    public void setEntityID(Long entityID) {
        this.entityID = entityID;
    }
}
