package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.models.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
