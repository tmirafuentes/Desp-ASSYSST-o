package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.Deloading;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface DeloadingRepository extends CrudRepository<Deloading, Long> {
    Deloading findDeloadingByDeloadCode(String deload_code);
}
