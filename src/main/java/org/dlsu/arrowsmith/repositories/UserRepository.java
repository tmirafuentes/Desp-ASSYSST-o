package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.College;
import org.dlsu.arrowsmith.classes.CourseOffering;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUserId(Long id);
    User findByUsername(String username);
    User findByFirstNameContainsAndLastNameContains(String firstName, String lastName);
    ArrayList<User> findAllByCollege(College college);
    ArrayList<User> findAllByUserType(String user_type);
}
