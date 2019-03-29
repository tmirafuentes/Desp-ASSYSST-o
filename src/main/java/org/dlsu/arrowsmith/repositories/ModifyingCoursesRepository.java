package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Building;
import org.dlsu.arrowsmith.classes.main.ModifyingCourses;
import org.dlsu.arrowsmith.classes.main.OnlineUsers;
import org.springframework.data.repository.CrudRepository;

public interface ModifyingCoursesRepository extends CrudRepository<ModifyingCourses, Long> {
    ModifyingCourses findByUserIdAndOfferingId(Long userId, Long offeringId);
    ModifyingCourses findByOfferingId(Long offeringId);
    ModifyingCourses findByUserId(Long userId);
}
