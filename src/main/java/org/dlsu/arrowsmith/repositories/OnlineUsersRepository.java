package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Building;
import org.dlsu.arrowsmith.classes.main.OnlineUsers;
import org.springframework.data.repository.CrudRepository;

public interface OnlineUsersRepository extends CrudRepository<OnlineUsers, Long> {
    OnlineUsers findByUserId(Long id);
}