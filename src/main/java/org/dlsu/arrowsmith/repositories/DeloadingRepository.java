package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Deloading;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DeloadingRepository extends CrudRepository<Deloading, Long> {
    Deloading findDeloadingByDeloadCode(String deload_code);
    ArrayList<Deloading> findAllByDeloadType(String deload_type);
}
