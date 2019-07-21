package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

/*
 *  ASSYSTX2
 *  WORKSPACE CONTROLLER
 *
 *  Contents:
 *  URL Mappings and REST Requests
 *  for creating, modifying, retrieving,
 *  and dissolving course offerings.
 *  This also includes assigning a room
 *  and faculty.
 */

@RestController
@RequestMapping({"/"})
public class WorkspaceController
{
    /*
     *  SERVICES
     *
     */

    @Autowired
    private OfferingService offeringService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messages;

    /*
     *  CREATE NEW OFFERING
     *  URL MAPPING
     *
     */

    /* Retrieve a list of possible courses from the input for add course offering */
    @GetMapping(value = "/autocomplete-course-code")
    public Response retrieveSuggestedCourses()
    {
        Iterator suggestedCourses = offeringService.retrieveSuggestedCourses();

        return new Response("Done", suggestedCourses);
    }

    /* Create a new course offering through the course code and section */
    @PostMapping(value = "/create-new-offering")
    public Response createNewOffering(@RequestBody CreateOfferingDTO dto)
    {
        /* Create DTO */
        CourseOffering newOffering = new CourseOffering();

        /* Check if Course exists */
        Course selectedCourse = offeringService.retrieveCourseByCourseCode(dto.getCourseCode());
        if(offeringService.retrieveCourseByCourseCode(dto.getCourseCode()) == null)
            return new Response("Error", messages.getMessage("message.noCourseExists", null, null));
        newOffering.setCourse(selectedCourse);

        /* Check if section is taken */
        String assignedSection = dto.getSection();
        if(offeringService.retrieveOfferingByCourseCodeAndSection(dto.getCourseCode(), assignedSection) != null)
            return new Response("Error", messages.getMessage("message.assignSectionTaken", null, null));
        newOffering.setSection(assignedSection);

        /* Add other basic information */
        newOffering.setTerm(userService.retrieveCurrentTerm());
        newOffering.setType("Regular");

        /* Save Course Offering into the database */
        offeringService.saveCourseOffering(newOffering);

        /* Return response */
        return new Response("Done", messages.getMessage("message.addOffering", null, null));
    }

    /*
     *  DISPLAY OFFERINGS
     *  URL MAPPING
     *
     */

    /* Retrieve an initial partial list of course offerings */
    @GetMapping(value = "/show-offerings")
    public Response retrievePartialOfferings(Model model)
    {
        /* Get current term */
        Term term = userService.retrieveCurrentTerm();

        /* Retrieve a partial list of course offerings */
        Iterator allOfferings = offeringService.retrieveAllOfferingsByTerm(term);

        /* Check if empty list or not */
        if (!allOfferings.hasNext())
        {
            /* Return a response */
            return new Response("Empty", messages.getMessage("message.noOfferings", null, null));
        }
        else
        {
            /* Convert partial list of course offerings into DTOs */
            Iterator partialOfferingDTOs = transferListOfferingToDTO(allOfferings);

            /* Return a response */
            return new Response("Done", partialOfferingDTOs);
        }
    }

    /*
     *  DISPLAY OFFERINGS
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /* Transfer a CourseOffering instance to a Data Transfer Object */
    private DisplayOfferingDTO transferToDisplayOfferingDTO(CourseOffering offering)
    {
        DisplayOfferingDTO displayOfferingDTO = new DisplayOfferingDTO();

        /* Offering ID */
        displayOfferingDTO.setOfferingID(offering.getOfferingId());

        /* Course Code */
        displayOfferingDTO.setCourseCode(offering.getCourse().getCourseCode());

        /* Section */
        if (!offering.getSection().equals(""))
            displayOfferingDTO.setSection(offering.getSection());
        else
            displayOfferingDTO.setSection("None");

        /* Faculty Name */
        if (offering.getFaculty() != null)
            displayOfferingDTO.setFacultyName(offering.getFaculty().getLastName() + ", " + offering.getFaculty().getFirstName());
        else
            displayOfferingDTO.setFacultyName("Unassigned");

        /* Days */
        boolean day1Done = false;
        for (Days day : offering.getDaysSet()) {
            /* Class Day */
            if (!day1Done)
                displayOfferingDTO.setDay1(day.getclassDay());
            else
                displayOfferingDTO.setDay2(day.getclassDay());

            /* Room */
            if (day.getRoom() != null && !day1Done)
                displayOfferingDTO.setRoomCode(day.getRoom().getRoomCode());
            else if (day.getRoom() != null && day1Done &&
                     !day.getRoom().getRoomCode().equals(displayOfferingDTO.getRoomCode()))
                displayOfferingDTO.setRoomCode(displayOfferingDTO.getRoomCode() + "/" + day.getRoom().getRoomCode());
            else if (day.getRoom() == null || day.getRoom().getRoomId() == 11111111)
                displayOfferingDTO.setRoomCode("Unassigned");

            /* Timeslot */
            if (!day.getbeginTime().equals("") && !day.getendTime().equals(""))
            {
                if (day.getbeginTime().length() == 3)
                {
                    displayOfferingDTO.setStartTime("0" + day.getbeginTime().charAt(0) + ":" + day.getbeginTime().substring(1, 3));
                } else if (day.getbeginTime().length() == 4) {
                    displayOfferingDTO.setStartTime(day.getbeginTime().substring(0, 2) + ":" + day.getbeginTime().substring(2, 4));
                }
                if (day.getendTime().length() == 3)
                {
                    displayOfferingDTO.setEndTime("0" + day.getendTime().charAt(0) + ":" + day.getendTime().substring(1, 3));
                } else if (day.getendTime().length() == 4) {
                    displayOfferingDTO.setEndTime(day.getendTime().substring(0, 2) + ":" + day.getendTime().substring(2, 4));
                }
            }
            else
            {
                displayOfferingDTO.setStartTime("00:00");
                displayOfferingDTO.setEndTime("00:00");
            }
            day1Done = true;
        }
        if (displayOfferingDTO.getDay1() == '\0')
        {
            displayOfferingDTO.setDay1('-');
            displayOfferingDTO.setRoomCode("Unassigned");
            displayOfferingDTO.setStartTime("00:00");
            displayOfferingDTO.setEndTime("00:00");
            displayOfferingDTO.setDay2('-');
        }
        if (displayOfferingDTO.getDay1() != '\0' &&
            displayOfferingDTO.getDay2() != '\0')
        {
            char tempDay1 = displayOfferingDTO.getDay1();
            char tempDay2 = displayOfferingDTO.getDay2();

            if((tempDay1 == 'W' && tempDay2 == 'M') || (tempDay1 == 'H' && tempDay2 == 'T'))
            {
                char temp = tempDay1;
                tempDay1 = tempDay2;
                tempDay2 = temp;
            }

            displayOfferingDTO.setDay1(tempDay1);
            displayOfferingDTO.setDay2(tempDay2);
        }

        return displayOfferingDTO;
    }

    /* Transfer a list of CourseOfferings to each Data Transfer Object */
    private Iterator transferListOfferingToDTO(Iterator listOfferings)
    {
        ArrayList<DisplayOfferingDTO> displayOfferingDTOArrayList = new ArrayList<>();
        while (listOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) listOfferings.next();
            DisplayOfferingDTO newDTO = transferToDisplayOfferingDTO(offering);
            displayOfferingDTOArrayList.add(newDTO);
        }
        return displayOfferingDTOArrayList.iterator();
    }

    /*
     *  MODIFY OFFERINGS
     *  URL MAPPING
     *
     */

    /* Update the selected course offering's section */
    @PostMapping(value = "/update-offering-section")
    public Response updateCourseOfferingSection(@RequestBody EditSectionDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(dto.getOfferingID());

        /* Update course offering's select */
        selectedOffering.setSection(dto.getSection());
        offeringService.saveCourseOffering(selectedOffering);

        return new Response("Done", null);
    }

    /* Update the selected course offering's type */
    @PostMapping(value = "/update-offering-type")
    public Response updateCourseOfferingType(@RequestBody EditOfferingTypeDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(dto.getOfferingID());

        /* Update course offering's select */
        selectedOffering.setType(dto.getOfferingType());
        offeringService.saveCourseOffering(selectedOffering);

        /* Send different messages */
        if(dto.getOfferingType().equals("Regular"))
            return new Response("Done", messages.getMessage("message.markRegularOffering", null, null));
        else if(dto.getOfferingType().equals("Special"))
            return new Response("Done", messages.getMessage("message.markSpecialClass", null, null));

        return new Response("Done", messages.getMessage("message.DissolvedOffering", null, null));
    }
}
