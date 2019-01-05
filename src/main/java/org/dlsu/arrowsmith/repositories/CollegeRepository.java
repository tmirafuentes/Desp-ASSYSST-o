package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.College;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface CollegeRepository extends CrudRepository<College, Long> {
    College findCollegeByCollege_code(String code);
    College findCollegeByCollege_name(String name);
    College findCollegeByCollege_id(Long id);
}
