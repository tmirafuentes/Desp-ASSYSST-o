package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Building;
import org.springframework.data.repository.CrudRepository;

public interface BuildingRepository extends CrudRepository<Building, Long> {
    Building findBuildingByBldgId(Long bldg_id);
    Building findBuildingByBldgName(String bldg_name);
}
