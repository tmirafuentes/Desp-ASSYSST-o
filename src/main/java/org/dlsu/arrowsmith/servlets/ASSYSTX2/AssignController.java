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
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping({"/", "/assign-room", "/assign-faculty"})
public class AssignController
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

    /* Update the selected course offering's room assignment */
    @PostMapping(value = "/update-offering-room")
    public Response updateCourseOfferingRoom(@RequestBody AssignRoomDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(dto.getCourseCode(), dto.getSection());

        /* Find Room Object */
        Room newRoom = offeringService.retrieveRoomByRoomCode(dto.getRoomCode());

        /* Update Days Object */
        Iterator daysList = offeringService.retrieveAllDaysByOffering(selectedOffering);

        /* Has currently assigned days */
        if (daysList.hasNext())
        {
            /* Delete Previous Days and replace with new ones */
            while (daysList.hasNext()) {
                Days dayInstance = (Days) daysList.next();
                offeringService.deleteSpecificDay(dayInstance);
            }
        }

        /* Save First Day */
        Days day1 = transferAssignRoomDTOToDays(dto, newRoom, selectedOffering, 1);
        offeringService.saveDays(day1);

        /* OPTIONAL - Save Second Day */
        if (dto.getDay2() != '-')
        {
            Days day2 = transferAssignRoomDTOToDays(dto, newRoom, selectedOffering, 2);
            offeringService.saveDays(day2);
        }

        return new Response("Done", messages.getMessage("message.assignRoom", null, null));
    }

    /*
     *  ASSIGN ROOM
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /* Transfer an AssignRoomDTO instance to a Days Object */
    private Days transferAssignRoomDTOToDays(AssignRoomDTO dto, Room room, CourseOffering selectedOffering, int dayNum)
    {
        Days newDay = new Days();

        /* Letter Day */
        if (dayNum == 2)
            newDay.setclassDay(dto.getDay2());
        else
            newDay.setclassDay(dto.getDay1());

        /* Start Time */
        if (dayNum == 2)
            newDay.setbeginTime(dto.getStartTimeDay2().replace(":", ""));
        else
            newDay.setbeginTime(dto.getStartTimeDay1().replace(":", ""));

        /* End Time */
        if (dayNum == 2)
            newDay.setendTime(dto.getEndTimeDay2().replace(":", ""));
        else
            newDay.setendTime(dto.getEndTimeDay1().replace(":", ""));

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

    /*
     *  ASSIGN FACULTY
     *  URL MAPPING
     *
     */

    /* Update the selected course offering's faculty assignment */
    @PostMapping(value = "/update-offering-faculty")
    public Response updateCourseOfferingFaculty(@RequestBody AssignFacultyDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(dto.getOfferingID());

        /* Retrieve faculty from database */
        User selectedFaculty = userService.findUserByFirstNameLastName(dto.getFacultyName());

        /* Assign Faculty to Course Offering */
        selectedOffering.setFaculty(selectedFaculty);
        offeringService.saveCourseOffering(selectedOffering);

        /* TODO: Update Faculty Load of chosen faculty */

        return new Response("Done", null);
    }

}
