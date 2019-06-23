package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.dtos.RevHistoryLinkDto;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.repositories.*;
import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;
import org.dlsu.arrowsmith.revisionHistory.ModifiedEntityTypeEntity;
import org.dlsu.arrowsmith.security.SecurityService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
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
    private ConcernRepository concernRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RevisionHistoryRepository revisionHistoryRepository;
    @Autowired
    private ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;
    @Autowired
    private TermRepository termRepository;

    /* Services */
    @Autowired
    private SecurityService securityService;

    /* Encryptor */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /* Entity Manager */
    @Autowired
    private EntityManager entityManager;

    /**
     **
     ** USER
     ** CRUD FUNCTIONS
     **
     */

    /*** Create New User ***/
    public void createNewUser(User u)
    {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userRepository.save(u);
    }

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

    /*** Retrieve User by Username ***/
    public User findUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
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

    /*** Retrieve all Users by User Type ***/
    public Iterator findAllUsersByUserType(String type) {
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAllByUserType(type);
        return allUsers.iterator();
    }

    /* Retrieve all Faculty */
    public Iterator retrieveAllFaculty()
    {
        return userRepository.findAllByUserType("Faculty").iterator();
    }

    /* Retrieve All Faculty that are active */
    public Iterator retrieveAllActiveFaculty()
    {
        return userRepository.findAllByUserTypeAndActive("Faculty", true).iterator();
    }

    /* Retrieve All Faculty that are inactive */
    public Iterator retrieveAllInactiveFaculty()
    {
        return userRepository.findAllByUserTypeAndActive("Faculty", false).iterator();
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
     ** REVISION HISTORY
     ** CRUD FUNCTIONS
     **
     */

    public AuditedRevisionEntity findAREById(Long revisionId)
    {
        return (AuditedRevisionEntity) revisionHistoryRepository.findAuditedRevisionEntityById(revisionId);
    }

    /* Retrieve Most Recent Entry */
    public RevHistoryLinkDto findLatestRevisionEntity()
    {
        return (RevHistoryLinkDto) retrieveRevHistoryOfferings().next();
    }

    /* Retrieve All Revision History for Course Offering and Days and sort by most recent */
    public Iterator retrieveRevHistoryOfferings()
    {
        /* Get all entities */
        ArrayList<AuditedRevisionEntity> revisionEntities = (ArrayList<AuditedRevisionEntity>)revisionHistoryRepository.findAll();

        /* Create DTO ArrayList */
        ArrayList<RevHistoryLinkDto> allRevisions = new ArrayList<>();

        /* Loop through entries and modify it for DTO */
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        for(AuditedRevisionEntity are : revisionEntities)
        {
            /* Query Course Offering Entities or Days */
            AuditQuery courseOfferingQuery = auditReader.createQuery().forRevisionsOfEntity(CourseOffering.class, true, true)
                                                                        .add(AuditEntity.revisionNumber().eq(are.getId()));
            AuditQuery daysQuery = auditReader.createQuery().forRevisionsOfEntity(Days.class, true, true)
                                                                        .add(AuditEntity.revisionNumber().eq(are.getId()));

            /* Only include Course Offering and Days revisions */
            if(courseOfferingQuery.getResultList().size() > 0 || daysQuery.getResultList().size() > 0)
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
        }

        return allRevisions.iterator();
    }

    /* Retrieve Specific Modified Entity Type Entity */
    public ArrayList<ModifiedEntityTypeEntity> findMETEById(AuditedRevisionEntity are)
    {
        return (ArrayList<ModifiedEntityTypeEntity>) modifiedEntityTypeEntityRepository.findModifiedEntityTypeEntityByRevision(are);
    }

    public Term retrieveCurrentTerm()
    {
        return termRepository.findTermByCurrTermIsTrue();
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
