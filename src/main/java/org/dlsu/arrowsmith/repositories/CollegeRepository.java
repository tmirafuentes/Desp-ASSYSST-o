package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.College;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface CollegeRepository extends CrudRepository<College, Long> {
    College findCollegeByCollegeCode(String code);
    College findCollegeByCollegeName(String name);
    College findCollegeByCollegeId(Long id);
}
