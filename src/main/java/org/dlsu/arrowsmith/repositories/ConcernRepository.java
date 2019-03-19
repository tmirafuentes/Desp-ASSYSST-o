package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Concern;
import org.dlsu.arrowsmith.classes.main.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ConcernRepository extends CrudRepository<Concern, Long> {
    ArrayList<Concern> findAllBySender(User user);
    ArrayList<Concern> findAllByReceiver(User user);
    ArrayList<Concern> findAllBySenderOrReceiver(User sender, User receiver);
}
