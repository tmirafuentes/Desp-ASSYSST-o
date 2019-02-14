package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.Building;
import org.dlsu.arrowsmith.classes.Room;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RoomRepository extends CrudRepository<Room, Long> {
    ArrayList<Room> findAllByBuilding(Building building);
    Room findRoomByRoomCode(String roomCode);
}
