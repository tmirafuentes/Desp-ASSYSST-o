package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.Course;
import org.dlsu.arrowsmith.classes.CourseOffering;
import org.dlsu.arrowsmith.classes.Department;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
    ArrayList<CourseOffering> findAllByFaculty(User user);
    ArrayList<CourseOffering> findAllByFacultyAndStartAYAndEndAYAndTerm(User faculty, int startAY, int end_AY, int term);
    ArrayList<CourseOffering> findAllByCourseAndStartAYAndEndAYAndTerm(Course course, int startAY, int end_AY, int term);
    ArrayList<CourseOffering> findAllByStatusAndStartAYAndEndAYAndTerm(String status, int startAY, int end_AY, int term);
    ArrayList<CourseOffering> findAllByCourseDepartmentAndStartAYAndEndAYAndTerm(Department department, int startAY, int end_AY, int term);
    CourseOffering findCourseOfferingByOfferingId(Long offering_id);
    ArrayList<CourseOffering> findAllByStartAYAndEndAYAndTerm(int startAY, int end_AY, int term);
}
