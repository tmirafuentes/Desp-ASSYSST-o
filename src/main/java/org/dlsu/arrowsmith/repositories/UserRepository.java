package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserId(Long userId);
    User findByUsername(String username);
}