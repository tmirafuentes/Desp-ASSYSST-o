package org.dlsu.arrowsmith.repositories;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;
import org.dlsu.arrowsmith.revisionHistory.ModifiedEntityTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ModifiedEntityTypeEntityRepository extends CrudRepository<ModifiedEntityTypeEntity, Long> {
    ArrayList<ModifiedEntityTypeEntity> findModifiedEntityTypeEntityByRevision(AuditedRevisionEntity auditedRevisionEntity);
}
