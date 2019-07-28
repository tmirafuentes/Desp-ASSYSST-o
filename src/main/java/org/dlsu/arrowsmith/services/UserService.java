package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.RecentChangesDTO;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.repositories.*;
import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;
import org.dlsu.arrowsmith.revisionHistory.ModifiedEntityTypeEntity;
import org.dlsu.arrowsmith.security.SecurityService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class UserService {
    /* Repositories */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private UserActivityRepository userActivityRepository;

    /* Services */
    @Autowired
    private SecurityService securityService;

    /* Encryptor */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     **
     ** USER
     ** CRUD FUNCTIONS
     **
     */

    /*** Create New User ***/
    public void createNewUser(User u, ArrayList<Role> roles)
    {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        u.setRoles(new HashSet<>(roles));
        userRepository.save(u);
    }

    /*** Update New User ***/
    public void updateUser(User u)
    {
        userRepository.save(u);
    }

    /*** Retrieve User by ID number ***/
    public User findUserByIDNumber(Long idNumber)
    {
        return userRepository.findUserByUserId(idNumber);
    }

    /*** Retrieve User by First Name and Last Name ***/
    public User findUserByFirstNameLastName(String givenName)
    {
        /* If given name is unassigned, return null */
        if(givenName.equals("Unassigned"))
            return null;

        /* Else, parse given name and search into the database */
        String[] facultyName = givenName.split(", ");
        return userRepository.findByFirstNameContainsAndLastNameContains(facultyName[1], facultyName[0]);
    }

    /*** Retrieve all Users and return sorted list of their names ***/
    public Iterator findAllUsers()
    {
        /* Retrieve all general users */
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();

        /* Get the names and format them for sorting */
        ArrayList<String> allUsersParsed = new ArrayList<>();
        for(User s: allUsers)
        {
            String newUser = s.getLastName() + ", " + s.getFirstName();
            allUsersParsed.add(newUser);
        }

        /* Sort by last name */
        Collections.sort(allUsersParsed, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        return allUsersParsed.iterator();
    }

    /* Retrieve all Faculty */
    public Iterator retrieveAllFaculty()
    {
        return userRepository.findAllByUserTypeNot("Academic Programming Officer").iterator();
    }

    /* Retrieve All Faculty that are active */
    public Iterator retrieveAllActiveFaculty() { return userRepository.findAllByUserTypeAndActive("Faculty", true).iterator(); }

    /* Retrieve All Faculty that are inactive */
    public Iterator retrieveAllInactiveFaculty() { return userRepository.findAllByUserTypeAndActive("Faculty", false).iterator(); }

    /*** Update Password of User ***/
    public void updateUserPassword(User user, String newPassword)
    {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserActivity retrieveUserActivity(User user)
    {
        return userActivityRepository.findUserActivityByUserId(user.getUserId());
    }

    public void saveUserActivity(UserActivity userActivity)
    {
        userActivityRepository.save(userActivity);
    }

    /*** Delete User ***/
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     **
     ** ROLE
     ** CRUD FUNCTIONS
     **
     */

    /*** Add Role ***/
    public void saveRole(Role r)
    {
        roleRepository.save(r);
    }

    /*** Retrieve Role Name ***/
    public Role findRoleByName(String name)
    {
        return roleRepository.findByRoleName(name);
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */

    public Term retrieveCurrentTerm()
    {
        return termRepository.findTermByCurrTermIsTrue();
    }

    public User retrieveUser() {
        Long idNumber = Long.parseLong(securityService.findLoggedInUsername());
        return userRepository.findUserByUserId(idNumber);
    }
}