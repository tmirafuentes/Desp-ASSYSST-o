package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.dtos.RevHistoryLinkDto;
import org.dlsu.arrowsmith.classes.main.Concern;
import org.dlsu.arrowsmith.classes.main.Role;
import org.dlsu.arrowsmith.classes.main.User;
import org.dlsu.arrowsmith.repositories.*;
import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;
import org.dlsu.arrowsmith.revisionHistory.ModifiedEntityTypeEntity;
import org.dlsu.arrowsmith.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    /* Repositories */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConcernRepository concernRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RevisionHistoryRepository revisionHistoryRepository;
    @Autowired
    private ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;

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
    public void createNewUser(User u) {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userRepository.save(u);
    }

    public void createNewUser(User u, ArrayList<Role> roles) {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        u.setRoles(new HashSet<>(roles));
        userRepository.save(u);
    }

    /*** Update New User ***/
    public void updateUser(User u) {
        userRepository.save(u);
    }

    /*** Retrieve User by ID number ***/
    public User findUserByIDNumber(Long idNumber) {
        return userRepository.findUserByUserId(idNumber);
    }

    /*** Retrieve User by Username ***/
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /*** Retrieve User by First Name and Last Name ***/
    public User findUserByFirstNameLastName(String givenName) {
        if(givenName.equals("Unassigned"))
            return null;

        String[] facultyName = givenName.split(", ");
        return userRepository.findByFirstNameContainsAndLastNameContains(facultyName[1], facultyName[0]);
    }

    /*** Retrieve all Users ***/
    public Iterator findAllUsers() {
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();
        ArrayList<String> allUsersParsed = new ArrayList<>();
        for(User s: allUsers)
        {
            String newUser = s.getLastName() + ", " + s.getFirstName();
            allUsersParsed.add(newUser);
        }
        Collections.sort(allUsersParsed, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
        return allUsersParsed.iterator();
    }

    /*** Retrieve all Users by User Type ***/
    public Iterator findAllUsersByUserType(String type) {
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAllByUserType(type);
        return allUsers.iterator();
    }

    /*** Update Password of User ***/
    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /*** Delete User ***/
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    /**
     **
     ** CONCERNS
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Concerns */
    public void saveConcern(Concern concern) {
        concernRepository.save(concern);
    }

    /* Retrieve All Concerns By Sender */
    public Iterator retrieveAllConcernsBySender(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllBySender(user);
        return concerns.iterator();
    }

    /* Retrieve All Concerns By Receiver */
    public Iterator retrieveAllConcernsByReceiver(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiver(user);
        return concerns.iterator();
    }
    public int retrieveNumberConcernsByBoolean(User user, boolean ack)
    {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiverAndAcknowledged(user, ack);
        return concerns.size();
    }
    /* Retrieve All Concerns By Receiver or Sender */
    public Iterator retrieveAllConcernsByUser(User sender, User receiver) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllBySenderOrReceiver(sender, receiver);
        return concerns.iterator();
    }

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
     ** REVISION HISTORY
     ** CRUD FUNCTIONS
     **
     */

    public AuditedRevisionEntity findAREById(Long revisionId)
    {
        return (AuditedRevisionEntity) revisionHistoryRepository.findAuditedRevisionEntityById(revisionId);
    }

    /* Retrieve All Revision History */
    public Iterator retrieveAllRevHistory()
    {
        /* Get all entities */
        ArrayList<AuditedRevisionEntity> revisionEntities = (ArrayList<AuditedRevisionEntity>)revisionHistoryRepository.findAll();

        /* Create DTO */
        ArrayList<RevHistoryLinkDto> allRevisions = new ArrayList<>();

        /* Loop through entries and modify it for DTO */
        for(AuditedRevisionEntity are : revisionEntities)
        {
            /* Create Temp Object */
            RevHistoryLinkDto temp = new RevHistoryLinkDto();
            /* Assign Timestamp */
            temp.setTimestamp(are.getDateModified());
            /* Assign User */
            User revUser = findUserByIDNumber(Long.parseLong(are.getFullName()));
            temp.setFullname(revUser.getFirstName() + " " + revUser.getLastName());
            /* Assign Position */
            temp.setPosition(revUser.getUserType());
            /* Assign Rev ID */
            temp.setRevNumber(are.getId());

            allRevisions.add(0, temp);
        }

        return allRevisions.iterator();
    }

    public ArrayList<ModifiedEntityTypeEntity> findMETEById(AuditedRevisionEntity are)
    {
        return (ArrayList<ModifiedEntityTypeEntity>) modifiedEntityTypeEntityRepository.findModifiedEntityTypeEntityByRevision(are);
    }

    /***
     *
     * OTHER FUNCTIONS
     *
     */

    public User retrieveUser() {
        Long idNumber = Long.parseLong(securityService.findLoggedInUsername());
        return userRepository.findUserByUserId(idNumber);
    }
    public Long retrieveUserID() {
        Long idNumber = Long.parseLong(securityService.findLoggedInUsername());
        return idNumber;
    }
}
