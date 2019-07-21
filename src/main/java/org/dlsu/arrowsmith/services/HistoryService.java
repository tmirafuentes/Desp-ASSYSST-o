package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.RecentChangesDTO;
import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.DeloadInstance;
import org.dlsu.arrowsmith.classes.main.User;
import org.dlsu.arrowsmith.repositories.RevisionHistoryRepository;
import org.dlsu.arrowsmith.revisionHistory.AuditedRevisionEntity;
import org.dlsu.arrowsmith.revisionHistory.ModifiedEntityTypeEntity;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;

@Service
public class HistoryService
{
    /* Repositories */
    @Autowired
    private RevisionHistoryRepository revisionHistoryRepository;

    /* Services */
    @Autowired
    private UserService userService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private OfferingService offeringService;

    /* Entity Manager */
    @Autowired
    private EntityManager entityManager;

    /**
     **
     ** REVISION HISTORY
     ** CRUD FUNCTIONS
     **
     */

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
                if (offering != null && offering.getTerm() == userService.retrieveCurrentTerm() ||
                        deloadInstance != null && deloadInstance.getTerm() == userService.retrieveCurrentTerm())
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
        User revUser = userService.findUserByIDNumber(Long.parseLong(are.getUserID()));
        dto.setFullName(revUser.getLastName() + ", " + revUser.getFirstName());

        /* Assign Timestamp */
        dto.setTimestamp(are.getDateModified());

        /* Assign Position */
        dto.setPosition(revUser.getUserType());

        /* Assign Revision ID */
        dto.setRevNumber(are.getId());

        return dto;
    }

    /* Determine the history log for DTO */
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
}
