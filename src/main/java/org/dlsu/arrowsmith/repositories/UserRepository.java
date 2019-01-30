package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.College;
import org.dlsu.arrowsmith.classes.CourseOffering;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUserId(Long id);
    ArrayList<User> findUsersByCollege(College college);
    ArrayList<User> findUsersByUserTypeEquals(String user_type);
}
