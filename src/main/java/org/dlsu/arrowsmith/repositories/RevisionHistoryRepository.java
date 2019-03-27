package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;
import org.springframework.data.repository.CrudRepository;

public interface RevisionHistoryRepository extends CrudRepository<AuditedRevisionEntity, Long> {
    AuditedRevisionEntity findAuditedRevisionEntityById(Long revisionId);
}
