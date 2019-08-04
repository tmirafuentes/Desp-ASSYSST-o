package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.User;
import org.dlsu.arrowsmith.classes.main.UserActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserActivityRepository extends CrudRepository<UserActivity, Long>
{
    UserActivity findUserActivityByUserId(Long userID);
    ArrayList<UserActivity> findAllByLastOfferingModified(Long offeringID);
}
