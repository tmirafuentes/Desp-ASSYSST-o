package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.Days;
import org.dlsu.arrowsmith.classes.main.Term;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        try
        {
            /* Convert DTO to Course Offering */
            CourseOffering newOffering = new CourseOffering();
            newOffering.setCourse(offeringService.retrieveCourseByCourseCode(dto.getCourseCode()));
            newOffering.setSection(dto.getSection());
            newOffering.setTerm(userService.retrieveCurrentTerm());
            newOffering.setType("Regular");

            /* Save Course Offering into the database */
            offeringService.saveCourseOffering(newOffering);
        }
        catch(Exception e) {e.printStackTrace();}
        finally
        {
            /* Return response */
            return new Response("Done", messages.getMessage("message.addOffering", null, null));
        }
    }

    /*
     *  FILTER OFFERINGS
     *  URL MAPPING
     *
     */

    /* Retrieve courses offered for a specific term */
    @GetMapping(value = "/retrieve-filter-courses")
    public Response retrieveFilterCourses(Model model)
    {
        /* Get current term */
        Term term = userService.retrieveCurrentTerm();

        /* Retrieve unique courses from offerings */

    }

    /*
     *  DISPLAY OFFERINGS
     *  URL MAPPING
     *
     */

    /* Retrieve an initial partial list of course offerings */
    @GetMapping(value = "/show-offerings")
    public Response retrievePartialOfferings(Model model, @RequestParam(value = "page", required = false) int pageNumber)
    {
        /* Get current term */
        Term term = userService.retrieveCurrentTerm();

        /* Retrieve a partial list of course offerings */
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);
        Page partialOfferings = offeringService.retrievePartialCourseOfferings(term, pageRequest);

        /* Check if empty list or not */
        if (partialOfferings.getTotalPages() == 0)
        {
            /* Return a response */
            return new Response("Empty", messages.getMessage("message.noOfferings", null, null));
        }
        else
        {
            /* Convert partial list of course offerings into DTOs */
            Iterator partialOfferingDTOs = transferListOfferingToDTO(partialOfferings.getContent().iterator());

            /* Put other page details into DTO */
            PageOfferingDTO pageOfferingDTO = new PageOfferingDTO();
            pageOfferingDTO.setCurrPageNum(partialOfferings.getNumber());
            pageOfferingDTO.setHasNext(partialOfferings.hasNext());
            pageOfferingDTO.setHasPrev(partialOfferings.hasPrevious());
            pageOfferingDTO.setTotalPages(partialOfferings.getTotalPages());
            pageOfferingDTO.setPageSize(partialOfferings.getSize());
            pageOfferingDTO.setCurrPartialOfferings(partialOfferingDTOs);

            /* Return a response */
            return new Response("Done", pageOfferingDTO);
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
            if (day.getRoom() != null)
                displayOfferingDTO.setRoomCode(day.getRoom().getRoomCode());
            else if (day.getRoom() == null || day.getRoom().getRoomId() == 11111111)
                displayOfferingDTO.setRoomCode("Unassigned");

            /* Timeslot */
            if (!day.getbeginTime().equals("") && !day.getendTime().equals("")) {
                if (day.getbeginTime().length() == 3) {
                    displayOfferingDTO.setStartTime("0" + day.getbeginTime().charAt(0) + ":" + day.getbeginTime().substring(1, 3));
                } else if (day.getbeginTime().length() == 4) {
                    displayOfferingDTO.setStartTime(day.getbeginTime().substring(0, 2) + ":" + day.getbeginTime().substring(2, 4));
                }
                if (day.getendTime().length() == 3) {
                    displayOfferingDTO.setEndTime("0" + day.getendTime().charAt(0) + ":" + day.getendTime().substring(1, 3));
                } else if (day.getendTime().length() == 4) {
                    displayOfferingDTO.setEndTime(day.getendTime().substring(0, 2) + ":" + day.getendTime().substring(2, 4));
                }
            } else {
                displayOfferingDTO.setStartTime("00:00");
                displayOfferingDTO.setEndTime("00:00");
            }
            day1Done = true;
        }
        if (displayOfferingDTO.getDay1() == '\0') {
            displayOfferingDTO.setDay1('-');
            displayOfferingDTO.setRoomCode("Unassigned");
            displayOfferingDTO.setStartTime("00:00");
            displayOfferingDTO.setEndTime("00:00");
            displayOfferingDTO.setDay2('-');
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

        return new Response("Done", null);
    }
}
