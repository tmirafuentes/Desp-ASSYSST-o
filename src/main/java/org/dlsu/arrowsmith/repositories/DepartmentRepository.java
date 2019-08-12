package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.College;
import org.dlsu.arrowsmith.classes.main.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Department findDepartmentByDeptCode(String deptCode);
    ArrayList<Department> findAllByCollege(College college);
}
