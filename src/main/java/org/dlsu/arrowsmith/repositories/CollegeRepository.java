package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.College;
import org.springframework.data.repository.CrudRepository;

public interface CollegeRepository extends CrudRepository<College, Long> {
    College findCollegeByCollegeCode(String code);
    College findCollegeByCollegeName(String name);
    College findCollegeByCollegeId(Long id);
}
