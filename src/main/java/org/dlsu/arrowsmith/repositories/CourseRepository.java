package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.models.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
