package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Course;
import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.Department;
import org.dlsu.arrowsmith.classes.main.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Long> {
    ArrayList<CourseOffering> findAllByFaculty(User user);
    ArrayList<CourseOffering> findAllByFacultyAndStartAYAndEndAYAndTerm(User faculty, int startAY, int end_AY, int term);
    ArrayList<CourseOffering> findAllByCourseAndStartAYAndEndAYAndTerm(Course course, int startAY, int end_AY, int term);
    ArrayList<CourseOffering> findAllByStatusAndStartAYAndEndAYAndTerm(String status, int startAY, int end_AY, int term);
    ArrayList<CourseOffering> findAllByCourseDepartmentAndStartAYAndEndAYAndTerm(Department department, int startAY, int end_AY, int term);
    CourseOffering findCourseOfferingByOfferingId(Long offering_id);
    ArrayList<CourseOffering> findAllByStartAYAndEndAYAndTerm(int startAY, int end_AY, int term);
    Page<CourseOffering> findAllByStartAYAndEndAYAndTerm(int startAY, int end_AY, int term, Pageable pageable);
    ArrayList<CourseOffering> findAllByCourseCourseCode(String courseCode);
}
