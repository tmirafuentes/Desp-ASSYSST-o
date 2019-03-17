package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.ModifyRoomDto;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.classes.dtos.RoomDto;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RestController
@RequestMapping({"/apo", "/cvc"})
public class RestWebController {
    /*** Services ***/
    @Autowired
    private OfferingService offeringService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyService facultyService;

    private ArrayList<OfferingModifyDto> filteredOfferings = new ArrayList<OfferingModifyDto>();
    private ArrayList<CourseOffering> offerings = new ArrayList<>();
    private ArrayList<Room> applicableRooms = new ArrayList<>();
    private int filterCount = 0;
    /***
     *
     *  ACTIVE
     *  URL MAPPING
     *
     */
    /* Filter the current pool of offerings to the term value specified*/
    @PostMapping(value = "/term-filter")
    public Response showOfferingTerms(@RequestBody Integer searchTerm, Model model)
    {

        Iterator allOfferings;
        if(offerings.size() == 0 && filterCount == 0)
            allOfferings = offeringService.generateSortedCourseOfferings(2016, 2017, 1);//gets all generated course offerings
        else
            allOfferings = offerings.iterator();

        offerings.clear();
        filteredOfferings.clear();
        filterCount++;
        /* Convert to DTO */
        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();
            OfferingModifyDto currDTO;
            if(searchTerm == offering.getTerm()) {
                /* Transfer to DTO */
                currDTO = transferToDTO(offering);
                this.offerings.add(offering);//update list of currently processed offerings
                this.filteredOfferings.add(currDTO);//update list of currently processed offerings for transfering
            }

        }

        /* Create Response Object */
        Response response = new Response();
        if(offerings.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        //response.setData(filteredOfferings);
        //response.setData(listOfferDtos);
        //model.addAttribute("allOfferings", listOfferDtos.iterator());

        return response;
    }
    /* Filter the current pool of offerings to the room value specified*/
    @PostMapping(value = "/room-filter")
    public Response showOfferingRoom(@RequestBody String searchTerm, Model model)
    {
        boolean isAdded = false;
        Iterator allOfferings;
        if(offerings.size() == 0 && filterCount == 0)
            allOfferings = offeringService.generateSortedCourseOfferings(2016, 2017, 1);//gets all generated course offerings
        else
            allOfferings = offerings.iterator();

        offerings.clear();
        filteredOfferings.clear();
        filterCount++;
        /* Convert to DTO */
        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();
            OfferingModifyDto currDTO;
            for(Days s: offering.getDaysSet())
            {
                if(searchTerm.equals(s.getRoom().getRoomCode()) && !isAdded) {
                    isAdded = true;
                    /* Transfer to DTO */
                    currDTO = transferToDTO(offering);
                    this.offerings.add(offering);//update list of currently processed offerings
                    this.filteredOfferings.add(currDTO);//update list of currently processed offerings for transfering
                }
            }
            isAdded = false;

        }

        /* Create Response Object */
        Response response = new Response();
        if(offerings.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        //response.setData(filteredOfferings);
        //response.setData(listOfferDtos);
        //model.addAttribute("allOfferings", listOfferDtos.iterator());

        return response;
    }
    /* Filter the current pool of offerings to the term value specified*/
    @PostMapping(value = "/time-filter")
    public Response showOfferingTimeSlot(@RequestBody String searchTerm, Model model)
    {
        boolean isAdded = false;
        Iterator allOfferings;
        if(offerings.size() == 0 && filterCount == 0)
            allOfferings = offeringService.generateSortedCourseOfferings(2016, 2017, 1);//gets all generated course offerings
        else
            allOfferings = offerings.iterator();

        offerings.clear();
        filteredOfferings.clear();
        filterCount++;
        /* Convert to DTO */
        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();
            OfferingModifyDto currDTO;
            for(Days s: offering.getDaysSet())
            {
                if(searchTerm.equals(s.getbeginTime() + " - " + s.getendTime()) && !isAdded) {
                    isAdded = true;
                    /* Transfer to DTO */
                    currDTO = transferToDTO(offering);
                    this.offerings.add(offering);//update list of currently processed offerings
                    this.filteredOfferings.add(currDTO);//update list of currently processed offerings for transfering
                }
            }
            isAdded = false;

        }

        /* Create Response Object */
        Response response = new Response();
        if(offerings.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        //response.setData(filteredOfferings);
        //response.setData(listOfferDtos);
        //model.addAttribute("allOfferings", listOfferDtos.iterator());

        return response;
    }
    /* Filter the current pool of offerings to the room type value specified*/
    @PostMapping(value = "/type-filter")
    public Response showOfferingClassType(@RequestBody String searchTerm, Model model)
    {
        boolean isAdded = false;
        Iterator allOfferings;
        if(offerings.size() == 0 && filterCount == 0)
            allOfferings = offeringService.generateSortedCourseOfferings(2016, 2017, 1);//gets all generated course offerings
        else
            allOfferings = offerings.iterator();

        offerings.clear();
        filteredOfferings.clear();
        filterCount++;
        /* Convert to DTO */
        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();
            OfferingModifyDto currDTO;
            for(Days s: offering.getDaysSet())
            {
                if(searchTerm.equals(s.getRoom().getRoomType()) && !isAdded) {
                    isAdded = true;
                    /* Transfer to DTO */
                    currDTO = transferToDTO(offering);
                    this.offerings.add(offering);//update list of currently processed offerings
                    this.filteredOfferings.add(currDTO);//update list of currently processed offerings for transfering
                }
            }
            isAdded = false;

        }

        /* Create Response Object */
        Response response = new Response();
        if(offerings.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        //response.setData(filteredOfferings);
        //response.setData(listOfferDtos);
        //model.addAttribute("allOfferings", listOfferDtos.iterator());

        return response;
    }
    /* Filter the current pool of offerings to the term value specified*/
    @PostMapping(value = "/search-courses")
    public Response showOfferingSearch(@RequestBody String searchTerm, Model model)
    {
        Iterator allOfferings;
        if(offerings.size() == 0 && filterCount == 0)
            allOfferings = offeringService.generateSortedCourseOfferings(2016, 2017, 1);//gets all generated course offerings
        else
            allOfferings = offerings.iterator();

        offerings.clear();
        filteredOfferings.clear();
        filterCount++;
        /* Convert to DTO */
        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();
            OfferingModifyDto currDTO;
                if(searchTerm.toUpperCase().equals(offering.getCourse().getCourseCode().toUpperCase()) || searchTerm.toUpperCase().contains(offering.getCourse().getCourseCode().toUpperCase())) {
                    /* Transfer to DTO */
                    currDTO = transferToDTO(offering);
                    this.offerings.add(offering);//update list of currently processed offerings
                    this.filteredOfferings.add(currDTO);//update list of currently processed offerings for transfering
                }

        }

        /* Create Response Object */
        Response response = new Response();
        if(offerings.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        //response.setData(filteredOfferings);
        //response.setData(listOfferDtos);
        //model.addAttribute("allOfferings", listOfferDtos.iterator());

        return response;
    }
    /* Retrieve All Filtered Course Offerings through GET */
    @GetMapping(value = "/get-filtered-offerings")
    public Response showAllFilteredOfferings(Model model)
    {
        System.out.println("Whatever it takes");
        /* Create Response Object */
        Response response = new Response();
        System.out.println(filteredOfferings.size());
        if(filteredOfferings.size() != 0)
        {
            response.setData(filteredOfferings);
            response.setStatus("Done");
        }
        else
        {
            response.setStatus("Error");
        }
        return response;
    }
    /* Retrieve All Course Offerings through GET */
    @GetMapping(value = "/show-offerings")
    public Response showAllOfferings(Model model)
    {
        /* Create new list for course offerings */
        Iterator allOfferings = offeringService.generateSortedCourseOfferings(2016, 2017, 1);

        /* Convert to DTO */
        ArrayList<OfferingModifyDto> listOfferDtos = new ArrayList<OfferingModifyDto>();
        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();

            /* Transfer to DTO */
            OfferingModifyDto currDTO = transferToDTO(offering);

            listOfferDtos.add(currDTO);
        }

        /* Create Response Object */
        Response response = new Response();
        response.setStatus("Done");
        response.setData(listOfferDtos);
        model.addAttribute("allOfferings", listOfferDtos.iterator());

        return response;
    }

    /* Modify Course Offering through POST */
    @PostMapping(value = "/modify-offering")
    public Response modifyCourseOffering(@RequestBody OfferingModifyDto offering)
    {

        /* Retrieve Offering from Database */
        CourseOffering currOffering = offeringService.retrieveCourseOffering(offering.getOfferingId());

        /* Course Offering Section */
        currOffering.setSection(offering.getClassSection());

        /* Course Offering Type */
        currOffering.setStatus(offering.getClassStatus());

        /* Find Room Object */
        Room newRoom = offeringService.retrieveRoomByRoomCode(offering.getRoomCode());

        /* Update Days Object */
        Set<Days> daysSet = currOffering.getDaysSet();

        boolean noConflicts = true;
        if (daysSet == null)
        {
            // If Day 1 is not null or "-" in the form
            if(!(offering.getDay1() == '-') && noConflicts)
            {
                Days newDay1 = new Days();
                newDay1.setclassDay(offering.getDay1());
                newDay1.setbeginTime(offering.getStartTime());
                newDay1.setendTime(offering.getEndTime());
                newDay1.setCourseOffering(currOffering);
                newDay1.setRoom(newRoom);

                daysSet.add(newDay1);
            }

            // If Day 2 is not null or "-" in the form
            if(!(offering.getDay2() == '-'))
            {
                Days newDay2 = new Days();
                newDay2.setclassDay(offering.getDay2());
                newDay2.setbeginTime(offering.getStartTime());
                newDay2.setendTime(offering.getEndTime());
                newDay2.setCourseOffering(currOffering);
                newDay2.setRoom(newRoom);

                daysSet.add(newDay2);
            }
        }
        else
        {
            boolean isDay1Done = false;
            for (Days dayInstance : daysSet)
            {
                // If Day 1 is not null or "-" in the form
                if(!(offering.getDay1() == '-') && !isDay1Done)
                {
                    dayInstance.setclassDay(offering.getDay1());
                    dayInstance.setbeginTime(offering.getStartTime());
                    dayInstance.setendTime(offering.getEndTime());
                    dayInstance.setCourseOffering(currOffering);
                    dayInstance.setRoom(newRoom);

                    isDay1Done = true;
                    continue;
                }
                // If Day 1 is null or "-" in the form
                else if(offering.getDay1() == '-' && !isDay1Done)
                {
                    daysSet.remove(dayInstance);
                    isDay1Done = true;
                    continue;
                }

                // If Day 2 is not null or "-" in the form
                if(!(offering.getDay2() == '-') && isDay1Done)
                {
                    dayInstance.setclassDay(offering.getDay2());
                    dayInstance.setbeginTime(offering.getStartTime());
                    dayInstance.setendTime(offering.getEndTime());
                    dayInstance.setCourseOffering(currOffering);
                    dayInstance.setRoom(newRoom);
                }
                // If Day 2 is null or "-" in the form
                else if(offering.getDay2() == '-' && isDay1Done)
                {
                    daysSet.remove(dayInstance);
                }
            }
        }

        /* Faculty */
        User currFaculty = currOffering.getFaculty();
        User newFaculty = userService.findUserByFirstNameLastName(offering.getFaculty());

        if (currFaculty.getUserId() == 11111111 && currFaculty.getUserId() != newFaculty.getUserId())
        {
            // Retrieve Faculty Load of current faculty
            FacultyLoad currFacultyLoad = facultyService.retrieveFacultyLoadByFaculty(currOffering.getStartAY(), currOffering.getEndAY(),
                    currOffering.getTerm(), currFaculty);

            // Subtract Units to Faculty Load
            currFacultyLoad.setTeachingLoad(currFacultyLoad.getTeachingLoad() - currOffering.getCourse().getUnits());

            // Save current faculty load to the database
            facultyService.saveFacultyLoad(currFacultyLoad);

            // Assign faculty to Course Offering
            currOffering.setFaculty(newFaculty);

            // Retrieve Faculty Load of faculty
            FacultyLoad newFacultyLoad = facultyService.retrieveFacultyLoadByFaculty(currOffering.getStartAY(), currOffering.getEndAY(),
                    currOffering.getTerm(), newFaculty);

            // Add Units to Faculty Load
            newFacultyLoad.setTeachingLoad(newFacultyLoad.getTeachingLoad() + currOffering.getCourse().getUnits());

            // Save new faculty load to the database
            facultyService.saveFacultyLoad(newFacultyLoad);
        }
        else if(currFaculty.getUserId() == 11111111 && newFaculty != null)
        {
            currOffering.setFaculty(newFaculty);    // Assign faculty to Course Offering

            // Retrieve Faculty Load of faculty
            FacultyLoad newFacultyLoad = facultyService.retrieveFacultyLoadByFaculty(currOffering.getStartAY(), currOffering.getEndAY(),
                    currOffering.getTerm(), newFaculty);

            // Add Units to Faculty Load
            newFacultyLoad.setTeachingLoad(newFacultyLoad.getTeachingLoad() + currOffering.getCourse().getUnits());

            // Save new faculty load to the database
            facultyService.saveFacultyLoad(newFacultyLoad);
        }

        /* Save Course Offering to Database */
        offeringService.saveCourseOffering(currOffering);

        /* Create Response Object */
        Response response = new Response();
        response.setStatus("Done");
        response.setData(offering);

        return response;
    }

    /* Retrieve Specific Course Offering through POST */
    @PostMapping(value = "/find-offering")
    public Response findCourseOffering(@RequestBody Long offeringId)
    {
        /* Retrieve specific course offering from database */
        //CourseOffering selectedOffering = offeringService.retrieveCourseOffering(Long.parseLong(offeringId));
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(offeringId);

        /* Transfer to DTO for easier processing for front-end */
        OfferingModifyDto offeringDto = transferToDTO(selectedOffering);
        System.out.println(offeringDto.getStartTime() + offeringDto.getEndTime());
        /* Create new Response object */
        Response response = new Response();
        response.setStatus("Done");
        response.setData(offeringDto);

        return response;
    }

    /***
     *
     *  FUNCTIONS
     *
     */
    public OfferingModifyDto transferToDTO(CourseOffering offering)
    {
        //System.out.println("TRANSFER DTO");
        OfferingModifyDto modifyDto = new OfferingModifyDto();

        /* Offering ID */
        modifyDto.setOfferingId(offering.getofferingId());

        /* Course Code */
        modifyDto.setCourseCode(offering.getCourse().getCourseCode());

        /* Section */
        if (offering.getSection() != null)
            modifyDto.setClassSection(offering.getSection());
        else
            modifyDto.setClassSection("None");

        /* Offering Status/Type */
        if (offering.getStatus() != null)
            modifyDto.setClassStatus(offering.getStatus());
        else
            modifyDto.setClassStatus("Regular");

        /* Offering Faculty */
        if (offering.getFaculty() != null && offering.getFaculty().getUserId() != 11111111)
            modifyDto.setFaculty(offering.getFaculty().getLastName() + ", " + offering.getFaculty().getFirstName());
        else if (offering.getFaculty() != null && offering.getFaculty().getUserId() == 11111111)
            modifyDto.setFaculty("Unassigned");
        else if (offering.getFaculty() == null)
            modifyDto.setFaculty("Unassigned");

        /* Days */
        boolean day1Done = false;
        for (Days day : offering.getDaysSet())
        {
            /* Class Day */
            if (!day1Done)
                modifyDto.setDay1(day.getclassDay());
            else
                modifyDto.setDay2(day.getclassDay());

            /* Room */
            if (day.getRoom() != null)
                modifyDto.setRoomCode(day.getRoom().getRoomCode());
            else if (day.getRoom() == null || day.getRoom().getRoomId() == 11111111)
                modifyDto.setRoomCode("Unassigned");

            /* Timeslot */
            if (day.getbeginTime() != null && day.getendTime() != null) {
                if (day.getbeginTime().length() == 3) {
                    modifyDto.setStartTime(day.getbeginTime().charAt(0) + ":" + day.getbeginTime().substring(1, 3));
                    System.out.println(day.getbeginTime().charAt(0) + ":" + day.getbeginTime().substring(1, 3));
                } else if (day.getbeginTime().length() == 4) {
                    modifyDto.setStartTime(day.getbeginTime().substring(0, 2) + ":" + day.getbeginTime().substring(2, 4));
                    System.out.println(day.getbeginTime().substring(0, 2) + ":" + day.getbeginTime().substring(2, 4));
                }
                if (day.getendTime().length() == 3) {
                    modifyDto.setEndTime(day.getendTime().charAt(0) + ":" + day.getendTime().substring(1, 3));
                    System.out.println(day.getendTime().charAt(0) + ":" + day.getendTime().substring(1, 3));
                } else if (day.getendTime().length() == 4){
                    modifyDto.setEndTime(day.getendTime().substring(0, 2) + ":" + day.getendTime().substring(2, 4));
                    System.out.println(day.getendTime().substring(0, 2) + ":" + day.getendTime().substring(2, 4));
                }
                else {
                    modifyDto.setStartTime(day.getbeginTime());
                    modifyDto.setEndTime(day.getendTime());
                }
            }
            else
            {
                modifyDto.setStartTime(":");
                modifyDto.setEndTime(":");
            }
            day1Done = true;
        }
        if (modifyDto.getDay1() == '\0') {
            modifyDto.setDay1('-');
            modifyDto.setRoomCode("Unassigned");
            modifyDto.setStartTime(":");
            modifyDto.setEndTime(":");
            modifyDto.setDay2('-');
        }

        return modifyDto;
    }
    /* Find all rooms that are available at this time and day */
    @PostMapping(value = "/check-rooms")
    public Response showRoomsApplicable(@RequestBody ModifyRoomDto timeInfo)
    {
        //Get all characters
        char day1 = timeInfo.getDay1();
        char day2 = timeInfo.getDay2();
        String startTime = timeInfo.getStartTime();
        String endTime = timeInfo.getEndTime();

        ArrayList<Room> roomList = offeringService.roomRuleChecking(day1, day2, startTime, endTime);
        ArrayList<RoomDto> transferableroomList = new ArrayList<>();

        System.out.println(roomList.size());
        for(Room r: roomList)
        {
            RoomDto roomDto = new RoomDto();
            roomDto.setRoomCode(r.getRoomCode());
            roomDto.setRoomType(r.getRoomType());
            roomDto.setCapacity(r.getRoomCapacity());
            roomDto.setBuilding(r.getBuilding().getBldgName());
            transferableroomList.add(roomDto);
        }
        /* Create Response Object */
        Response response = new Response();
        if(roomList.size() <= 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        response.setData(transferableroomList);
        return response;
    }

}
