package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.*;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DaysRepository extends CrudRepository<Days, Long> {
    ArrayList<Days> findAllByCourseOffering(CourseOffering offering);
    ArrayList<Days> findAllByRoomAndCourseOffering_Term(Room room, Term term);
    ArrayList<Days> findAllByBeginTimeAndEndTime(String begin_time, String end_time);
    ArrayList<Days> findAllByRoomAndBeginTimeAndEndTime(Room room, String begin_time, String end_time);
    ArrayList<Days> findAllByRoomBuildingAndCourseOfferingTerm(Building building, Term term);
    ArrayList<Days> findAllByRoomBuildingAndBeginTimeAndEndTimeAndCourseOfferingTerm(Building building, String begin_time, String end_time, Term term);
    ArrayList<Days> findAllByCourseOffering_Term(Term term);
    Days findByDaysId(Long id);
}
