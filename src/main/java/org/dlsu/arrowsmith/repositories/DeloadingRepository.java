package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Deloading;
import org.springframework.data.repository.CrudRepository;

public interface DeloadingRepository extends CrudRepository<Deloading, Long> {
    Deloading findDeloadingByDeloadCode(String deload_code);
}
