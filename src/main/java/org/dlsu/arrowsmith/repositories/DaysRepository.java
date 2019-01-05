package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.CourseOffering;
import org.dlsu.arrowsmith.classes.Days;
import org.dlsu.arrowsmith.classes.Room;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DaysRepository extends CrudRepository<Days, Long> {
    ArrayList<Days> findAllByCourseOffering(CourseOffering offering);
    ArrayList<Days> findAllByRoom(Room room);
    ArrayList<Days> findAllByBegin_timeAndEnd_time(String begin_time, String end_time);
}
