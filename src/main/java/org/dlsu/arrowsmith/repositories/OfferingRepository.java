package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.models.Course;
import org.dlsu.arrowsmith.models.Offering;
import org.springframework.data.repository.CrudRepository;

public interface OfferingRepository extends CrudRepository<Offering, Long> {
}
