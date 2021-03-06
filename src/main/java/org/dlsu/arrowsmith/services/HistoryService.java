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
    public ArrayList<RecentChangesDTO> retrieveOfferingHistory(CourseOffering selectedOffering)
    {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        /* Create query for Course Offering */
        AuditQuery courseOfferingQuery = auditReader.createQuery()
                .forRevisionsOfEntity(CourseOffering.class, false, true);

        if(selectedOffering != null)
                courseOfferingQuery.add(AuditEntity.id().eq(selectedOffering.getOfferingId()));

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

            if(tempARE.getId() < 60)
                continue;

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

        return history;
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
        /* Dissolved offering */
        else if(revisionType.name().equals("MOD") && offering.getType().equals("Dissolved"))
            return offeringName + " is dissolved";

        return "";
    }
}
