package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
