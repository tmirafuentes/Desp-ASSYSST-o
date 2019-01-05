package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.Department;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
}
