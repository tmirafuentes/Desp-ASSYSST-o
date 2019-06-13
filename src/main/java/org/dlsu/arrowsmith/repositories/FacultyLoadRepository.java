package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.*;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FacultyLoadRepository extends CrudRepository<FacultyLoad, Long> {
    ArrayList<FacultyLoad> findAllByTerm(Term term);
    FacultyLoad findFacultyLoadByTermAndFaculty(Term term, User faculty);
    ArrayList<FacultyLoad> findAllByTermAndDepartment(Term term, Department department);
    ArrayList<FacultyLoad> findAllByTermAndCollege(Term term, College college);
    ArrayList<FacultyLoad> findAllByFaculty(User faculty);
    FacultyLoad findFacultyLoadByLoadId(Long loadId);
}
