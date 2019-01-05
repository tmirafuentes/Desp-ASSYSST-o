package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.User;
import org.dlsu.arrowsmith.repositories.UserRepository;
import org.dlsu.arrowsmith.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class UserService {
    /* Repositories */
    @Autowired
    private UserRepository userRepository;

    /* Services */
    @Autowired
    private SecurityService securityService;

    /* Encryptor */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*** Create New User ***/
    public void createNewUser(User u) {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userRepository.save(u);
    }

    /*** Update New User ***/
    public void updateUser(User u) {
        userRepository.save(u);
    }

    /*** Retrieve User by ID number ***/
    public User findUserByIDNumber(Long idNumber) {
        return userRepository.findUserByUser_id(idNumber);
    }

    /*** Retrieve all Users ***/
    public Iterator findAllUsers() {
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();
        return allUsers.iterator();
    }

    /*** Retrieve all Users by User Type ***/
    public Iterator findAllUsersByUserType(String type) {
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findUsersByUser_typeEquals(type);
        return allUsers.iterator();
    }

    /*** Update Password of User ***/
    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    /***
     *
     * OTHER FUNCTIONS
     *
     */

    public User retrieveUser() {
        Long idNumber = Long.parseLong(securityService.findLoggedInUsername());
        return userRepository.findUserByUser_id(idNumber);
    }
}
