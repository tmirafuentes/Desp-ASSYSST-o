package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
@RequestMapping({"/"})
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

    @Autowired
    private MessageSource messages;

    /*
     *
     *  COURSE OFFERING MANAGEMENT
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
     *  COURSE OFFERING MANAGEMENT
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
     *
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
        /* Convert DTO to Course Offering */
        CourseOffering newOffering = new CourseOffering();
        newOffering.setCourse(offeringService.retrieveCourseByCourseCode(dto.getCourseCode()));
        newOffering.setSection(dto.getSection());
        newOffering.setTerm(userService.retrieveCurrentTerm());
        newOffering.setType("Regular");

        /* Save Course Offering into the database */
        offeringService.saveCourseOffering(newOffering);

        /* Return response */
        return new Response("Done", messages.getMessage("message.addOffering", null, null));
    }

    /*
     *
     *  ASSIGN ROOM
     *  URL MAPPING
     *
     */

    /* Retrieve all building names from the database. */
    @GetMapping(value = "/assign-room/retrieve-building-names")
    public Response retrieveBuildingNames()
    {
        Iterator allBuildings = offeringService.retrieveAllBuildings();

        return new Response("Done", allBuildings);
    }

    /* Retrieve all room names based on the building selected. */
    @PostMapping(value = "/assign-room/retrieve-room-names")
    public Response retrieveRoomNames(@RequestBody String buildingCode)
    {
        buildingCode = buildingCode.substring(0, buildingCode.length() - 1);

        /* Get Building selected */
        Building selectedBuilding = offeringService.retrieveBuildingByBuildingCode(buildingCode);

        /* Get all rooms of that building */
        Iterator allRooms = offeringService.retrieveAllRoomsByBuilding(selectedBuilding);

        return new Response("Done", allRooms);
    }

    @PostMapping(value = "/assign-room/retrieve-occupying-offerings")
    public Response retrieveOccupyingOfferings(@RequestBody String roomCode)
    {
        /* Clean input Room Code */
        roomCode = roomCode.substring(0, roomCode.length() - 1);

        /* Find equivalent room in database */
        Room selectedRoom = offeringService.retrieveRoomByRoomCode(roomCode);

        /* Find latest term */
        Term currentTerm = userService.retrieveCurrentTerm();

        /* Find all Days that occupy room */
        Iterator allDaysOccupied = offeringService.retrieveAllDaysByRoomAndTerm(selectedRoom, currentTerm);

        /* Iterate list to filter out offerings not in the selected term */
        ArrayList<OccupyOfferingDTO> allOccupiers = new ArrayList<>();
        while(allDaysOccupied.hasNext())
        {
            /* Retrieve Days object from the list */
            Days daysItem = (Days) allDaysOccupied.next();

            /* Transfer to DTO */
            OccupyOfferingDTO dto = new OccupyOfferingDTO();
            dto.setRoomCode(daysItem.getRoom().getRoomCode());
            dto.setCourseCode(daysItem.getCourseOffering().getCourse().getCourseCode());
            dto.setSection(daysItem.getCourseOffering().getSection());
            dto.setBeginTime(daysItem.getbeginTime());
            dto.setEndTime(daysItem.getendTime());
            dto.setDay(daysItem.getclassDay());

            /* Add to new list */
            allOccupiers.add(dto);
        }

        return new Response("Done", allOccupiers.iterator());
    }
}
