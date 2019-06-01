package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.classes.main.Course;
import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.Days;
import org.dlsu.arrowsmith.classes.main.Room;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping({"/assystx2", "/assystx2/apo", "/assystx2/cvc"})
public class NewRestController
{
    /*** Services ***/
    @Autowired
    private OfferingService offeringService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyService facultyService;

    @PersistenceContext
    private EntityManager entityManager;

    /*
     *
     *  URL MAPPING
     *
     */

    /* Retrieve an initial partial list of course offerings */
    @GetMapping(value = "/show-offerings")
    public Response retrievePartialOfferings(Model model, @RequestParam(value = "page", required = false) int pageNumber)
    {
        /* Retrieve a partial list of course offerings */
        PageRequest pageRequest = PageRequest.of(pageNumber, 15);
        Page partialOfferings = offeringService.retrievePartialCourseOfferings(2016, 2017, 1, pageRequest);

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

    /* Update the selected course offering's section */
    @PostMapping(value = "/update-offering-section")
    public Response updateCourseOfferingSection(@RequestBody EditSectionDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(dto.getOfferingID());

        /* Update the course offering's section */
        if (!selectedOffering.getSection().equals(dto.getSection()))
            selectedOffering.setSection(dto.getSection());

        /* Save updated course offering to database */
        offeringService.saveCourseOffering(selectedOffering);

        /* Return a response */
        return new Response("Done", dto);
    }

    /* Update the selected course offering's room assignment */
    @PostMapping(value = "/update-offering-room")
    public Response updateCourseOfferingRoom(@RequestBody AssignRoomDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(dto.getOfferingID());

        /* Find Room Object */
        Room newRoom = offeringService.retrieveRoomByRoomCode(dto.getRoomCode());

        /* Update Days Object */
        Set<Days> daysSet = selectedOffering.getDaysSet();

        boolean noConflicts = true;
        if (daysSet == null)        /* No current class days and room for the offering */
        {
            // If Input Day 1 is not null or "-" in the form
            if (!(dto.getDay1() == '-') && noConflicts)
            {
                /* Create a new Days object */
                Days newDay1 = transferAssignRoomDTOToDays(dto, newRoom, selectedOffering, 1);
                daysSet.add(newDay1);
                offeringService.saveDays(newDay1);
            }

            // If Day 2 is not null or "-" in the form
            if (!(dto.getDay2() == '-'))
            {
                /* Create a new Days object */
                Days newDay2 = transferAssignRoomDTOToDays(dto, newRoom, selectedOffering, 2);
                daysSet.add(newDay2);
                offeringService.saveDays(newDay2);
            }
        }
        else                        /* There is already an assigned days for the offering */
        {
            boolean isDay1Done = false;
            for (Days dayInstance : daysSet)
            {
                // If input Day 1 is not null or "-" in the form - update day instance
                if (!(dto.getDay1() == '-') && dto.getDay1() != dayInstance.getclassDay() && !isDay1Done)
                {
                    updateDaysInstance(dayInstance, dto, newRoom, selectedOffering, 1);
                    isDay1Done = true;
                    continue;
                }
                // If input Day 1 is null or "-" in the form - delete day instance
                else if (dto.getDay1() == '-' && !isDay1Done)
                {
                    daysSet.remove(dayInstance);
                    offeringService.deleteSpecificDay(dayInstance);
                    isDay1Done = true;
                    continue;
                }

                // If Day 2 is not null or "-" in the form
                if (!(dto.getDay2() == '-') && dto.getDay2() != dayInstance.getclassDay() && isDay1Done)
                {
                    updateDaysInstance(dayInstance, dto, newRoom, selectedOffering, 2);
                }
                // If Day 2 is null or "-" in the form
                else if (dto.getDay2() == '-' && isDay1Done)
                {
                    daysSet.remove(dayInstance);
                    offeringService.deleteSpecificDay(dayInstance);
                }
            }
        }

        selectedOffering.setDaysSet(daysSet);
        for (Days d : daysSet) {
            offeringService.saveDays(d);
        }

        return new Response("Done", null);
    }

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
        /* Convert DTO to Course Offering */
        CourseOffering newOffering = new CourseOffering();
        newOffering.setCourse(offeringService.retrieveCourseByCourseCode(dto.getCourseCode()));
        newOffering.setSection(dto.getSection());

        /* Put other details */
        newOffering.setStartAY(2016);
        newOffering.setEndAY(2017);
        newOffering.setTerm(1);
        newOffering.setStatus("Regular");

        /* Save Course Offering into the database */
        offeringService.saveCourseOffering(newOffering);

        /* Return response */
        return new Response("Done", null);
    }

    /*
     *
     *  FUNCTIONS
     *
     */

    /* Transfer a CourseOffering instance to a Data Transfer Object */
    private DisplayOfferingDTO transferToDisplayOfferingDTO(CourseOffering offering)
    {
        DisplayOfferingDTO displayOfferingDTO = new DisplayOfferingDTO();

        /* Offering ID */
        displayOfferingDTO.setOfferingID(offering.getofferingId());

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

    /* Transfer an AssignRoomDTO instance to a Days Object */
    private Days transferAssignRoomDTOToDays(AssignRoomDTO dto, Room room, CourseOffering selectedOffering, int dayNum)
    {
        Days newDay = new Days();

        /* Letter Day */
        if (dayNum == 1)
            newDay.setclassDay(dto.getDay1());
        else
            newDay.setclassDay(dto.getDay2());

        /* Start Time */
        newDay.setbeginTime(dto.getStartTime().replace(":", ""));

        /* End Time */
        newDay.setendTime(dto.getEndTime().replace(":", ""));

        /* Room */
        newDay.setRoom(room);

        /* Course Offering */
        newDay.setCourseOffering(selectedOffering);

        return newDay;
    }

    private void updateDaysInstance(Days dayInstance, AssignRoomDTO dto, Room room, CourseOffering selectedOffering, int dayNum)
    {
        Long daysId = dayInstance.getdaysId();
        dayInstance = transferAssignRoomDTOToDays(dto, room, selectedOffering, dayNum);
        dayInstance.setdaysId(daysId);
        offeringService.saveDays(dayInstance);
    }
}
