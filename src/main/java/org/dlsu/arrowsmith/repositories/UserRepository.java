package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.College;
import org.dlsu.arrowsmith.classes.main.Department;
import org.dlsu.arrowsmith.classes.main.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUserId(Long id);
    User findByUsername(String username);
    User findByFirstNameContainsAndLastNameContains(String firstName, String lastName);
    ArrayList<User> findAllByCollege(College college);
    ArrayList<User> findAllByUserType(String user_type);
    ArrayList<User> findAllByUserTypeNot(String user_type);
    ArrayList<User> findAllByUserTypeAndActive(String user_type, boolean active);
    User findUserByDepartmentAndUserType(Department department, String user_type);
    User findUserByCollegeAndUserType(College college, String user_type);
}
