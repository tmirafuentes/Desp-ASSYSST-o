package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Concern;
import org.dlsu.arrowsmith.classes.main.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ConcernRepository extends CrudRepository<Concern, Long> {
    Concern findConcernByConcernId(Long id);
    ArrayList<Concern> findAllBySender(User user);
    ArrayList<Concern> findAllByReceiver(User user);
    ArrayList<Concern> findAllBySenderOrReceiver(User sender, User receiver);
    ArrayList<Concern> findAllByReceiverAndAcknowledged(User receiver, boolean acknowledged);
    ArrayList<Concern> findAllByReceiverOrderByDateTimeCommittedDesc(User receiver);
    ArrayList<Concern> findAllBySenderOrderByDateTimeCommittedDesc(User sender);
}
