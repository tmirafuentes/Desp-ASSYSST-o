package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.College;
import org.dlsu.arrowsmith.classes.main.Course;
import org.dlsu.arrowsmith.classes.main.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseRepository extends CrudRepository<Course, Long> {
    ArrayList<Course> findAllByCollege(College college);
    ArrayList<Course> findAllByDepartment(Department department);
    Course findCourseByCourseCode(String course_code);
}
