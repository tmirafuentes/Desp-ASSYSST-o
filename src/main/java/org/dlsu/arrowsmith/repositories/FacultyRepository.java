package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.models.Course;
import org.dlsu.arrowsmith.models.Faculty;
import org.springframework.data.repository.CrudRepository;

public interface FacultyRepository extends CrudRepository<Faculty, Long> {
}
