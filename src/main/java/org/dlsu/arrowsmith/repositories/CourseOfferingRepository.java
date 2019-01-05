package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.CourseOffering;
import org.dlsu.arrowsmith.classes.User;
import org.dlsu.arrowsmith.classes.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Long> {
    ArrayList<CourseOffering> findAllByStart_AYAndEnd_AYAndTerm(String start_AY, String end_AY, int term);
    ArrayList<CourseOffering> findAllByFaculty(User user);
    ArrayList<CourseOffering> findAllByFacultyAndStart_AYAndEnd_AYAndTerm(User faculty, String start_AY, String end_AY, int term);
    ArrayList<CourseOffering> findAllByCourseAndStart_AYAndEnd_AYAndTerm(Course course,String start_AY, String end_AY, int term);
    CourseOffering findCourseOfferingByOffering_id(Long offering_id);
}
