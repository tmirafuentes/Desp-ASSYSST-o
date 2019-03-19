package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(String name);
}
