package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.College;
import org.dlsu.arrowsmith.classes.DegreeProgram;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DegreeProgramRepository extends CrudRepository<DegreeProgram, Long> {
    ArrayList<DegreeProgram> findAllByCollege(College college);
}
