package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.College;
import org.dlsu.arrowsmith.classes.main.DegreeProgram;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DegreeProgramRepository extends CrudRepository<DegreeProgram, Long> {
    ArrayList<DegreeProgram> findAllByCollege(College college);
}
