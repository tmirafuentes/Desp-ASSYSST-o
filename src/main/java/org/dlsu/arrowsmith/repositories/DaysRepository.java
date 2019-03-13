package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.Days;
import org.dlsu.arrowsmith.classes.main.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DaysRepository extends CrudRepository<Days, Long> {
    ArrayList<Days> findAllByCourseOffering(CourseOffering offering);
    ArrayList<Days> findAllByRoom(Room room);
    ArrayList<Days> findAllByBeginTimeAndEndTime(String begin_time, String end_time);
    ArrayList<Days> findAllByRoomAndBeginTimeAndEndTime(Room room, String begin_time, String end_time);
}
