package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface ConcernRepository extends CrudRepository<User, Long> {
}
