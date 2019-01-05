package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.Room;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
