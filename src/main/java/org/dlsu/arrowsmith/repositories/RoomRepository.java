package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.models.Course;
import org.dlsu.arrowsmith.models.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
