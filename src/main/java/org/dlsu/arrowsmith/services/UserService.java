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
    private FacultyService facultyService;

    @Autowired
    private OfferingService offeringService;

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

    /* Retrieve Individual Concern */
    public Concern findConcernByConcernId(Long concernId)
    {
        return concernRepository.findConcernByConcernId(concernId);
    }

    /* Retrieve All Concerns By Sender */
    public Iterator retrieveAllConcernsBySender(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllBySenderOrderByDateTimeCommittedAsc(user);
        return concerns.iterator();
    }

    /* Retrieve All Concerns By Receiver */
    public Iterator retrieveAllConcernsByReceiver(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiverOrderByDateTimeCommittedAsc(user);
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

    /* Retrieve Department Head through department */
    public User retrieveDepartmentHead(Department department, String userType)
    {
        return userRepository.findUserByDepartmentAndUserType(department, userType);
    }

    /* Retrieve Department Head through department */
    public User retrieveAcadAssistant(College college)
    {
        return userRepository.findUserByCollegeAndUserType(college, "Academic Programming Officer");
    }

    public Iterator retrievePartialConcernsByReceiver(User receiver)
    {
        ArrayList<Concern> partialConcerns = concernRepository.findAllByReceiverOrderByDateTimeCommittedAsc(receiver);
        return partialConcerns.iterator();
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
    public RecentChangesDTO findLatestRevisionEntity()
    {
        return (RecentChangesDTO) retrieveWorkspaceHistory().next();
    }

    /* Retrieve Course Offering History */
    public Iterator retrieveOfferingHistory(CourseOffering selectedOffering)
    {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        /* Create query for courseOffering */
        AuditQuery courseOfferingQuery = auditReader.createQuery()
                .forRevisionsOfEntity(CourseOffering.class, false, true)
                .add(AuditEntity.id().eq(selectedOffering.getOfferingId()));

        /* Get result list */
        List<Object[]> audit = courseOfferingQuery.getResultList();

        /* Create DTO list */
        ArrayList<RecentChangesDTO> history = new ArrayList<>();

        /* Traverse each item on the list */
        Date prevDate = null;
        for (Object[] a : audit)
        {
            /* Course Offering */
            CourseOffering tempOffering = (CourseOffering) a[0];

            /* Audited Revision Entity */
            AuditedRevisionEntity tempARE = (AuditedRevisionEntity) a[1];

            /* Revision Type */
            RevisionType revType = (RevisionType) a[2];

            /* Check Revised Entities */
            Set<ModifiedEntityTypeEntity> mete = tempARE.getModifiedEntityTypes();
            Iterator meteIterator = mete.iterator();
            boolean hasRoom = false, hasUser = false, hasDays = false;
            while(meteIterator.hasNext())
            {
                ModifiedEntityTypeEntity tempMETE = (ModifiedEntityTypeEntity) meteIterator.next();
                if(tempMETE.getEntityClassName().equals("Room"))
                    hasRoom = true;
                else if(tempMETE.getEntityClassName().equals("Days"))
                    hasDays = true;
                else if(tempMETE.getEntityClassName().equals("User"))
                    hasUser = true;
                else if(tempMETE.getEntityClassName().equals("CourseOffering"))
                    tempOffering = offeringService.retrieveCourseOffering(tempMETE.getEntityID());
            }

            /* Create Recent History DTO */
            RecentChangesDTO dto = createNewChangesDTO(tempARE);

            /* Check Date */
            if(prevDate != null && prevDate.equals(tempARE.getDateModified()))
                continue;

            /* Create Subject */
            String dtoSubject = determineRecentChangesSubject(true, hasRoom, hasUser, hasDays, tempOffering, revType);
            dto.setSubject(dtoSubject);

            /* Add to list */
            history.add(dto);

            prevDate = tempARE.getDateModified();
        }

        return history.iterator();
    }

    /* Retrieve All Revision History for Course Offering and Days and sort by most recent */
    public Iterator retrieveWorkspaceHistory()
    {
        /* Get all entities */
        ArrayList<AuditedRevisionEntity> revisionEntities = (ArrayList<AuditedRevisionEntity>)revisionHistoryRepository.findAll();

        /* Create DTO ArrayList */
        ArrayList<RecentChangesDTO> allRevisions = new ArrayList<>();

        /* Loop through entries and modify it for DTO */
        CourseOffering prevOffering = null;
        boolean filledPrevOffering = false;
        Date prevDate = null;
        for(AuditedRevisionEntity are : revisionEntities)
        {
            if (are.getId() < 60)
                continue;

            /* Query Course Offering, Days, Faculty Load, Deloading Entities
            AuditQuery courseOfferingQuery = auditReader.createQuery().forRevisionsOfEntity(CourseOffering.class, true, true)
                                                                        .add(AuditEntity.revisionNumber().eq(are.getId()));
                                                                        */

            /* Create DTO */
            RecentChangesDTO dto = createNewChangesDTO(are);

            /* Check Date */
            if(prevDate != null && prevDate.equals(are.getDateModified()))
                continue;

            /* Determine the subject by the entities updated */
            boolean hasOffering = false, hasUser = false, hasDays = false, hasDeloading = false;
            CourseOffering offering = null;
            DeloadInstance deloadInstance = null;
            Iterator entityTypes = are.getModifiedEntityTypes().iterator();

            while(entityTypes.hasNext())
            {
                ModifiedEntityTypeEntity entity = (ModifiedEntityTypeEntity) entityTypes.next();
                if (entity.getEntityClassName().equals("CourseOffering"))
                {
                    hasOffering = true;
                    offering = offeringService.retrieveCourseOffering(entity.getEntityID());
                }
                else if (entity.getEntityClassName().equals("Days"))
                {
                    hasDays = true;
                }
                else if (entity.getEntityClassName().equals("User"))
                    hasUser = true;
                else if (entity.getEntityClassName().equals("DeloadInstance"))
                {
                    hasDeloading = true;
                    deloadInstance = facultyService.retrieveDeloadInstanceByID(entity.getEntityID());
                }
            }

            try {
                boolean isADuplicate = false;

                String selectedOffering = offering.getCourse().getCourseCode() + " " + offering.getSection();

                /* Room assignment */
                if(hasOffering && hasDays)
                    dto.setSubject("Room assigned to " + selectedOffering);
                    /* Faculty assignment */
                else if(hasOffering && hasUser)
                    dto.setSubject("Faculty assigned to " + selectedOffering);
                    /* Dissolved OFfering */
                else if(hasOffering && !hasDays && !hasUser &&
                        offering.getType().equals("Dissolved"))
                    dto.setSubject(selectedOffering + " is dissolved");
                    /* Faculty deloading */
                else if(hasDeloading)
                    dto.setSubject("Faculty is deloaded");
                    /* Modified Offering */
                else
                    dto.setSubject(selectedOffering + " is modified");
            } catch(Exception e) { }
            finally
            {
                /* Save if current term */
                if (offering != null && offering.getTerm() == retrieveCurrentTerm() ||
                    deloadInstance != null && deloadInstance.getTerm() == retrieveCurrentTerm())
                    allRevisions.add(dto);
            }

            prevDate = are.getDateModified();
        }

        return allRevisions.iterator();
    }

    /* Create a template RecentChangesDTO */
    public RecentChangesDTO createNewChangesDTO(AuditedRevisionEntity are)
    {
        RecentChangesDTO dto = new RecentChangesDTO();

        /* Assign Name */
        User revUser = findUserByIDNumber(Long.parseLong(are.getUserID()));
        dto.setFullName(revUser.getLastName() + ", " + revUser.getFirstName());

        /* Assign Timestamp */
        dto.setTimestamp(are.getDateModified());

        /* Assign Position */
        dto.setPosition(revUser.getUserType());

        /* Assign Revision ID */
        dto.setRevNumber(are.getId());

        return dto;
    }

    public String determineRecentChangesSubject(boolean hasOffering, boolean hasRoom, boolean hasUser, boolean hasDays,
                                                CourseOffering offering, RevisionType revisionType)
    {
        if (!hasOffering)
            return "";

        String offeringName = offering.getCourse().getCourseCode() + " " + offering.getSection();

        /* Created new offering */
        if(revisionType.name().equals("ADD"))
            return offeringName + " is created";
        /* Timeslot and Room assigned/modified */
        else if(hasRoom && hasDays && revisionType.name().equals("MOD"))
            return "Timeslot and Room assigned to " + offeringName;
        /* Timeslot assigned/modified */
        else if(hasDays && revisionType.name().equals("MOD"))
            return "Timeslot assigned to " + offeringName;
        /* Faculty assigned/modified */
        else if(hasUser && revisionType.name().equals("MOD") && offering.getFaculty() != null)
            return "Faculty assigned to " + offeringName;
        /* Faculty unassigned/deloaded */
        else if(hasUser && revisionType.name().equals("MOD") && offering.getFaculty() == null)
            return "Faculty unassigned from " + offeringName;
        /* Section edited */
        else if(!hasDays && !hasRoom && !hasUser && revisionType.name().equals("MOD"))
            return offeringName + " is modified";
        else if(revisionType.name().equals("MOD") && offering.getType().equals("Dissolved"))
            return offeringName + " is dissolved";

        return "";
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
