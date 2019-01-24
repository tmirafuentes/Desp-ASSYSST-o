package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.College;
import org.dlsu.arrowsmith.classes.Department;
import org.dlsu.arrowsmith.classes.FacultyLoad;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface FacultyLoadRepository extends CrudRepository<FacultyLoad, Long> {
    ArrayList<FacultyLoad> findAllByStart_AYAndEnd_AYAndTerm(int start_ay, int end_ay, int term);
    ArrayList<FacultyLoad> findAllByStart_AYAndEnd_AYAndTermAndFaculty(int start_ay, int end_ay, int term, User faculty);
    ArrayList<FacultyLoad> findAllByStart_AYAndEnd_AYAndTermAndDepartment(int start_ay, int end_ay, int term, Department department);
    ArrayList<FacultyLoad> findAllByStart_AYAndEnd_AYAndTermAndCollege(int start_ay, int end_ay, int term, College college);
    ArrayList<FacultyLoad> findAllByFaculty(User faculty);
}
