package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dlsu.arrowsmith.classes.main.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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
    @GetMapping(value = "/retrieve-building-names")
    public Response retrieveBuildingNames()
    {
        Iterator allBuildings = offeringService.retrieveAllBuildings();

        return new Response("Done", allBuildings);
    }

    /* Retrieve all room names based on the building selected. */
    @PostMapping(value = "/retrieve-room-names")
    public Response retrieveRoomNames(@RequestBody ObjectNode request)
    {
        /* Retrieve building code from request */
        String buildingCode = request.get("buildingCode").asText();

        /* Retrieve course from user activity */
        UserActivity userActivity = userService.retrieveUserActivity(userService.retrieveUser());
        Course course = offeringService.retrieveCourseOffering(userActivity.getLastOfferingModified()).getCourse();
        String roomType = course.getRoomType();

        /* Get Building selected */
        Building selectedBuilding = offeringService.retrieveBuildingByBuildingCode(buildingCode);

        /* Get all rooms of that building */
        Iterator allRooms = offeringService.retrieveAllRoomsByBuildingAndRoomType(selectedBuilding, roomType);

        return new Response("Done", allRooms);
    }

    @PostMapping(value = "/retrieve-occupied-rooms")
    public Response retrieveOccupyingOfferings(@RequestBody ObjectNode request)
    {
        /* Get timeslot and building code from request */
        String timeslot = request.get("timeslot").asText();
        String[] timeSplit = timeslot.split("-");
        String bldgCode = request.get("bldgCode").asText();
        String testString = timeSplit[0] + " - " + timeSplit[1] + ": " + bldgCode;

        /* Retrieve building from building code */
        Building building = offeringService.retrieveBuildingByBuildingCode(bldgCode);

        /* Retrieve current term */
        Term currTerm = userService.retrieveCurrentTerm();

        /* Retrieve All Days from building */
        Iterator allDays = offeringService.retrieveAllDaysByBuildingAndTimeslot(building, timeSplit[0], timeSplit[1], currTerm);

        /* Retrieve All Rooms from building */
        Iterator allRooms = offeringService.retrieveAllRoomsByBuilding(building);

        /* Create list of OccupiedRoomDTOs */
        ArrayList<OccupiedRoomDTO> dtos = new ArrayList<>();
        while(allRooms.hasNext())
        {
            Room currRoom = (Room) allRooms.next();

            OccupiedRoomDTO dto = new OccupiedRoomDTO(currRoom.getRoomCode());
            dtos.add(dto);
        }

        /* Modify DTOs to accommodate occupied rooms */
        while(allDays.hasNext())
        {
            Days currDay = (Days) allDays.next();

            /* Check if matched room code */
            OccupiedRoomDTO occupiedRoom = null;
            for(OccupiedRoomDTO dto : dtos)
            {
                if(dto.getRoomCode().equals(currDay.getRoom().getRoomCode()))
                {
                    occupiedRoom = dto;
                    break;
                }
            }

            /* Mark availability of room */
            if(currDay.getclassDay() == 'M')
            {
                occupiedRoom.setAvailDay1(false);
                occupiedRoom.setOffDay1(currDay.getCourseOffering().getCourse().getCourseCode() + " " + currDay.getCourseOffering().getSection());
            }
            else if(currDay.getclassDay() == 'T')
            {
                occupiedRoom.setAvailDay2(false);
                occupiedRoom.setOffDay2(currDay.getCourseOffering().getCourse().getCourseCode() + " " + currDay.getCourseOffering().getSection());
            }
            else if(currDay.getclassDay() == 'W')
            {
                occupiedRoom.setAvailDay3(false);
                occupiedRoom.setOffDay3(currDay.getCourseOffering().getCourse().getCourseCode() + " " + currDay.getCourseOffering().getSection());
            }
            else if(currDay.getclassDay() == 'H')
            {
                occupiedRoom.setAvailDay4(false);
                occupiedRoom.setOffDay4(currDay.getCourseOffering().getCourse().getCourseCode() + " " + currDay.getCourseOffering().getSection());
            }
            else if(currDay.getclassDay() == 'F')
            {
                occupiedRoom.setAvailDay5(false);
                occupiedRoom.setOffDay5(currDay.getCourseOffering().getCourse().getCourseCode() + " " + currDay.getCourseOffering().getSection());
            }
            else if(currDay.getclassDay() == 'S')
            {
                occupiedRoom.setAvailDay6(false);
                occupiedRoom.setOffDay6(currDay.getCourseOffering().getCourse().getCourseCode() + " " + currDay.getCourseOffering().getSection());
            }
        }

        return new Response("Done", dtos.iterator());
    }

    /* Update the selected course offering's room assignment */
    @PostMapping(value = "/update-offering-room")
    public Response updateCourseOfferingRoom(@RequestBody AssignRoomDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(dto.getCourseCode(), dto.getSection());

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
        Room room1 = offeringService.retrieveRoomByRoomCode(dto.getRoomCode1());
        Days day1 = transferAssignRoomDTOToDays(dto, room1, selectedOffering, 1);
        offeringService.saveDays(day1);

        /* OPTIONAL - Save Second Day */
        if (dto.getDay2() != '-')
        {
            Room room2 = offeringService.retrieveRoomByRoomCode(dto.getRoomCode2());
            Days day2 = transferAssignRoomDTOToDays(dto, room2, selectedOffering, 2);
            offeringService.saveDays(day2);
        }

        /* Update User Activity */
        UserActivity userActivity = userService.retrieveUserActivity(userService.retrieveUser());
        userActivity.setLastOfferingModified(null);
        userActivity.setOfferingNotified(false);
        userService.saveUserActivity(userActivity);

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

    /* Retrieve all available faculty in a given term */
    @GetMapping(value = "/retrieve-available-faculty")
    public Response retrieveAvailableFaculty()
    {
        /* Find Chair's department */
        User currUser = userService.retrieveUser();
        Department userDept = currUser.getDepartment();

        /* Find latest term */
        Term currentTerm = userService.retrieveCurrentTerm();

        /* Retrieve all Faculty Load in the term */
        Iterator facultyLoads = facultyService.retrieveAllFacultyLoadByTerm(currentTerm, userDept);

        /* Transfer into DTO object */
        ArrayList<FacultyOptionDTO> dtos = new ArrayList<>();
        while(facultyLoads.hasNext())
        {
            /* Retrieve current load */
            FacultyLoad facultyLoad = (FacultyLoad) facultyLoads.next();

            if(!facultyLoad.isOnLeave() && facultyLoad.getFaculty().getUserType().equals("Faculty"))
            {
                dtos.add(transferFacultyLoadToFacultyOptionDTO(facultyLoad));
            }
        }

        /* Sort Alphabetically */
        Collections.sort(dtos, new Comparator<FacultyOptionDTO>(){
            public int compare(FacultyOptionDTO f1, FacultyOptionDTO f2) {
                return f1.getFacultyName().compareToIgnoreCase(f2.getFacultyName());
            }
        });

        return new Response("Done", dtos);
    }

    /* Retrieve available faculty who prefers the course in a given term */
    @GetMapping(value = "/retrieve-preferred-course-faculty")
    public Response retrievePreferredCourseFaculty()
    {
        return null;
    }

    /* Retrieve available faculty who previously taught the course in a given term */
    @GetMapping(value = "/retrieve-previous-experienced-faculty")
    public Response retrieveExperiencedFaculty()
    {
        /* Retrieve user activity */
        UserActivity userActivity = userService.retrieveUserActivity(userService.retrieveUser());
        Course selectedCourse = offeringService.retrieveCourseOffering(userActivity.getLastOfferingModified()).getCourse();

        /* Retrieve available faculty */
        ArrayList<FacultyOptionDTO> availableFaculty = (ArrayList<FacultyOptionDTO>) retrieveAvailableFaculty().getData();
        ArrayList<FacultyOptionDTO> experiencedFaculty = new ArrayList<FacultyOptionDTO>();

        for(FacultyOptionDTO faculty : availableFaculty)
        {
            User selectedFaculty = userService.findUserByFirstNameLastName(faculty.getFacultyName());

            /* Search for offering -- If yes, continue loop */
            if(offeringService.isCourseTaughtByFaculty(selectedFaculty, selectedCourse))
                experiencedFaculty.add(faculty);
        }

        return new Response("Done", experiencedFaculty.iterator());
    }

    /* Update the selected course offering's faculty assignment */
    @PostMapping(value = "/update-offering-faculty")
    public Response updateCourseOfferingFaculty(@RequestBody AssignFacultyDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(dto.getCourseCode(), dto.getSection());

        /* Retrieve faculty from database */
        User selectedFaculty = userService.findUserByFirstNameLastName(dto.getFacultyName());

        /* Check if same faculty as the one selected */
        if(selectedOffering.getFaculty() == selectedFaculty)
            return new Response("Done", messages.getMessage("message.assignFaculty", null, null));

        /* Get units */
        double loadUnits = selectedOffering.getCourse().getNumHours();

        /* Retrieve current term */
        Term currentTerm = userService.retrieveCurrentTerm();

        /* Check if super overload */
        FacultyLoad facultyLoad = facultyService.retrieveFacultyLoadByFaculty(currentTerm, selectedFaculty);
        if(facultyLoad.getTotalLoad() + loadUnits <= 16.0 &&
           facultyLoad.getPreparations() <= 3)
        {
            /* Remove Load from previous faculty if ever */
            if(selectedOffering.getFaculty() != null)
            {
                /* Remove Faculty from Course Offering */
                selectedOffering.setFaculty(null);
                offeringService.saveCourseOffering(selectedOffering);

                /* Deduct Academic Load from Faculty */
                facultyService.assignAcademicLoadToFaculty(currentTerm, selectedOffering.getFaculty(), loadUnits * -1);
            }

            /* Assign Faculty to Course Offering */
            selectedOffering.setFaculty(selectedFaculty);
            offeringService.saveCourseOffering(selectedOffering);

            /* Assign Academic Load to Faculty */
            if(selectedOffering.getType().equals("Special"))
                facultyService.assignAcademicLoadToFaculty(currentTerm, selectedFaculty, 0);
            else
                facultyService.assignAcademicLoadToFaculty(currentTerm, selectedFaculty, loadUnits);

            /* Update User Activity */
            UserActivity userActivity = userService.retrieveUserActivity(userService.retrieveUser());
            userActivity.setLastOfferingModified(null);
            userActivity.setOfferingNotified(false);
            userService.saveUserActivity(userActivity);

            return new Response("Done", messages.getMessage("message.assignFaculty", null, null));
        }

        return new Response("Overload", "This faculty has reached the maximum of 16 units or 3 preparations. Please select another faculty.");
    }

    /*
     *  ASSIGN FACULTY
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /* Transfer Faculty Load into Faculty Option DTO */
    private FacultyOptionDTO transferFacultyLoadToFacultyOptionDTO(FacultyLoad facultyLoad)
    {
        /* Retrieve faculty */
        User faculty = facultyLoad.getFaculty();

        /* Initialize DTO */
        FacultyOptionDTO dto = new FacultyOptionDTO();
        dto.setFacultyID(faculty.getUserId());
        dto.setFacultyName(faculty.getLastName() + ", " + faculty.getFirstName());
        dto.setFacultyPosition(faculty.getUserPosition());
        dto.setTeachingUnits(facultyLoad.getTeachingLoad());
        dto.setDeloadedUnits(facultyLoad.getAdminLoad() + facultyLoad.getResearchLoad() + facultyLoad.getNonacadLoad());

        return dto;
    }
}
