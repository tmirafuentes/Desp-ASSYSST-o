package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.services.ConcernsService;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

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
    private ConcernsService concernsService;

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

        /* Create Display Offering DTO */
        DisplayOfferingDTO newOfferingDTO = transferToDisplayOfferingDTO(newOffering);

        /* Return response */
        return new Response("Done", newOfferingDTO, messages.getMessage("message.addOffering", null, null));
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

        /* Convert partial list of course offerings into DTOs */
        Iterator partialOfferingDTOs = transferListOfferingToDTO(allOfferings);

        /* Return a response */
        return new Response("Done", partialOfferingDTOs);
    }

    /* Retrieve the teaching load of course offerings */
    @GetMapping(value = "/retrieve-teaching-load")
    public Response retrieveTeachingLoad(Model model)
    {
        /* Get current term */
        Term term = userService.retrieveCurrentTerm();

        /* Retrieve a partial list of course offerings */
        Iterator allOfferings = offeringService.retrieveAllOfferingsByFaculty(userService.retrieveUser(), term);

        /* Convert partial list of course offerings into DTOs */
        Iterator partialOfferingDTOs = transferListOfferingToDTO(allOfferings);

        /* Return a response */
        return new Response("Done", partialOfferingDTOs);
    }

    /* Retrieve the teaching load of course offerings */
    @PostMapping(value = "/retrieve-teaching-load")
    public Response retrieveTeachingLoad(Model model, @RequestBody ObjectNode request)
    {
        /* Get user */
        String facultyName = request.get("facultyName").asText();
        User selectedFaculty = userService.findUserByFirstNameLastName(facultyName);

        /* Get current term */
        Term term = userService.retrieveCurrentTerm();

        /* Retrieve a partial list of course offerings */
        Iterator allOfferings = offeringService.retrieveAllOfferingsByFaculty(selectedFaculty, term);

        /* Convert partial list of course offerings into DTOs */
        Iterator partialOfferingDTOs = transferListOfferingToDTO(allOfferings);

        /* Return a response */
        return new Response("Done", partialOfferingDTOs);
    }

    /*
     *  DISPLAY OFFERINGS
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /* Transfer a list of CourseOfferings to each Data Transfer Object */
    private Iterator transferListOfferingToDTO(Iterator listOfferings)
    {
        ArrayList<DisplayOfferingDTO> displayOfferingDTOArrayList = new ArrayList<>();
        while (listOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) listOfferings.next();
            DisplayOfferingDTO newDTO = transferToDisplayOfferingDTO(offering);

            /* Check if concern is related to offering */
            boolean relatedConcern = concernsService.retrieveAllUnacknowledgedConcernsByReceiver(userService.retrieveUser(), false, offering);
            newDTO.setRelatedConcern(relatedConcern);

            /* Check if offering is being modified */
            boolean offeringModified = userService.retrieveOfferingInUserActivity(offering);
            newDTO.setOfferingEdited(offeringModified);

            displayOfferingDTOArrayList.add(newDTO);
        }
        return displayOfferingDTOArrayList.iterator();
    }

    /* Transfer a CourseOffering instance to a Data Transfer Object */
    private DisplayOfferingDTO transferToDisplayOfferingDTO(CourseOffering offering)
    {
        DisplayOfferingDTO displayOfferingDTO = new DisplayOfferingDTO();

        /* Offering ID */
        displayOfferingDTO.setOfferingID(offering.getOfferingId());

        /* Offering Type */
        if(offering.getType().equals("Regular") && offering.getServiceTo() != null)
            displayOfferingDTO.setOfferingType("Service");
        else
            displayOfferingDTO.setOfferingType(offering.getType());

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

        if(offering.getDaysSet() == null)
        {
            displayOfferingDTO.setDay1('-');
            displayOfferingDTO.setRoomCode("Unassigned");
            displayOfferingDTO.setStartTime("00:00");
            displayOfferingDTO.setEndTime("00:00");
            displayOfferingDTO.setDay2('-');
        }
        else
        {
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
        }

        /* Combined Time and Days */
        displayOfferingDTO.setCombinedDays(displayOfferingDTO.getDay1() + " " + displayOfferingDTO.getDay2());
        displayOfferingDTO.setCombinedTime(displayOfferingDTO.getStartTime() + " - " + displayOfferingDTO.getEndTime());

        return displayOfferingDTO;
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
    public Response updateCourseOfferingType(@RequestBody ObjectNode request)
    {
        /* Retrieve data */
        String courseCode = request.get("courseCode").asText();
        String section = request.get("section").asText();
        String offeringType = request.get("offeringType").asText();

        System.out.println("Course Code = " + courseCode);

        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(courseCode, section);

        /* Reduce Faculty Load units */
        if(offeringType.equals("Special") && selectedOffering.getFaculty() != null)
        {
            facultyService.assignAcademicLoadToFaculty(userService.retrieveCurrentTerm(),
                                                       selectedOffering.getFaculty(),
                                                       selectedOffering.getCourse().getNumHours() * -1);
        }
        else if(offeringType.equals("Dissolved"))
        {
            /* Reduce Units */
            if (selectedOffering.getFaculty() != null)
            {
                facultyService.assignAcademicLoadToFaculty(userService.retrieveCurrentTerm(),
                        selectedOffering.getFaculty(),
                        selectedOffering.getCourse().getNumHours() * -1);

                /* Remove Faculty from offering */
                selectedOffering.setFaculty(null);
            }

            /* Remove Days from offering */
            if(selectedOffering.getDaysSet() != null)
            {
                Iterator days = selectedOffering.getDaysSet().iterator();
                while(days.hasNext())
                {
                    Days day = (Days) days.next();
                    offeringService.deleteSpecificDay(day);
                }
            }
        }

        /* Update course offering's type */
        selectedOffering.setType(offeringType);
        offeringService.saveCourseOffering(selectedOffering);

        /* Send different messages */
        if(offeringType.equals("Regular"))
            return new Response("Done", messages.getMessage("message.markRegularOffering", null, null));
        else if(offeringType.equals("Special"))
            return new Response("Done", messages.getMessage("message.markSpecialClass", null, null));

        return new Response("Done", messages.getMessage("message.markDissolvedOffering", null, null));
    }

    /* Service Course */
    @GetMapping(value = "/retrieve-all-departments")
    public Response retrieveAllDepartments()
    {
        Iterator allDepartments = facultyService.retrieveAllDepartments();
        ArrayList<SelectDeptDTO> dtos = new ArrayList<>();

        while(allDepartments.hasNext())
        {
            Department dept = (Department) allDepartments.next();
            SelectDeptDTO dto = new SelectDeptDTO();
            dto.setDeptName(dept.getDeptName());
            dto.setDeptCode(dept.getDeptCode());

            dtos.add(dto);
        }

        return new Response("Done", dtos.iterator());
    }

    @PostMapping(value = "/service-course")
    public Response serviceCourse(@RequestBody ObjectNode request)
    {
        /* Retrieve data */
        String courseCode = request.get("courseCode").asText();
        String section = request.get("section").asText();
        String deptCode = request.get("deptCode").asText();

        /* Retrieve Department */
        Department serviceDept = facultyService.retrieveDepartmentByDeptCode(deptCode);

        /* Retrieve Course Offering */
        CourseOffering courseOffering = offeringService.retrieveOfferingByCourseCodeAndSection(courseCode, section);

        /* Service the Course to another department */
        courseOffering.setServiceTo(serviceDept.getDeptId());
        offeringService.saveCourseOffering(courseOffering);

        /* Retrieve respective chairs */
        User senderChair = userService.retrieveUser();
        User receiverChair = concernsService.retrieveDepartmentHead(serviceDept, "Vice-Chair");

        /* Send a concern to inform other chair */
        Concern serviceConcern = new Concern();
        serviceConcern.setAcknowledged(false);
        serviceConcern.setDateTimeCommitted(LocalDateTime.now());
        serviceConcern.setSubject(courseCode + " " + section);
        serviceConcern.setMessage("Greetings! Our department would like your department to service this course. Thank you!");
        serviceConcern.setReceiver(receiverChair);
        serviceConcern.setSender(senderChair);
        concernsService.saveConcern(serviceConcern);

        return new Response("Done", messages.getMessage("message.markServiceCourse", null, null));
    }
}
