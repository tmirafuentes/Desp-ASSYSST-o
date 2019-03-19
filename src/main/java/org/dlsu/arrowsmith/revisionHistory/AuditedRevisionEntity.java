package org.dlsu.arrowsmith.revisionHistory;

import org.dlsu.arrowsmith.classes.User;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "revision_history")
@RevisionEntity(AuditingRevisionListener.class)
public class AuditedRevisionEntity {
    @RevisionNumber
    @Id
    @SequenceGenerator(name = "revisionSeq", sequenceName = "REVISION_DATOS_ID_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revisionSeq")
    private Long id;

    @RevisionTimestamp
    private Date dateModified;

    private String fullName;

    public AuditedRevisionEntity() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
