package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.*;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Filter;

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

    /***
     *
     *  ACTIVE
     *  URL MAPPING
     *
     */
    @PostMapping(value = "/search-course")
    public Response filterCoursesbyCourseCode(@RequestBody String courseCode, Model model) {
        ArrayList<CourseOffering> searchCourses = offeringService.retrieveCourseOfferingSearch(courseCode);

        ArrayList<OfferingModifyDto> listOfferDtos = convertToDTO(searchCourses.iterator());

        Response response = new Response();
        if (listOfferDtos.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        response.setData(listOfferDtos);
        return response;
    }

    @PostMapping(value = "/filter-days")
    public Response filterCoursesOnDay(@RequestBody String day, Model model) {
        char charDay = day.charAt(1);
        ArrayList<CourseOffering> dayCourses = offeringService.getAllCoursesOnDay(charDay);

        ArrayList<OfferingModifyDto> listOfferDtos = convertToDTO(dayCourses.iterator());

        /* Create Response Object */
        Response response = new Response();
            response.setStatus("Error");
        else
            response.setStatus("Done");

        response.setData(listOfferDtos);
        return response;
    }

    //General function for filtering
    @PostMapping(value = "/left-filter")
    public Response filterCourseOfferings(@RequestBody FilterDto filterValues, Model model) {
        ArrayList<ArrayList<CourseOffering>> filterHolder = new ArrayList<ArrayList<CourseOffering>>();
        //THIS IS WHERE INTERSECTION WILL HAPPEN BITCHES
        ArrayList<OfferingModifyDto> listOfferDtos = new ArrayList<>();
        ArrayList<CourseOffering> termFilter = new ArrayList<>();
        ArrayList<CourseOffering> classTypeFilter = new ArrayList<>();
        ArrayList<CourseOffering> roomTypeFilter = new ArrayList<>();
        ArrayList<CourseOffering> timeBlockFilter = new ArrayList<>();
        ArrayList<CourseOffering> holder = new ArrayList<>();
        if(filterValues.getTerm().equals("All") && filterValues.getClassType().equals("All") && filterValues.getRoomType().equals("All") &&
                filterValues.getTimeBlock().equals("All"))
            holder = offeringService.generateSortedArrayListCourseOfferings(2016, 2017, 1);
        else{
            //Get all results
            if(!filterValues.getTerm().equals("All"))
                termFilter =  offeringService.retrieveCourseOfferingsTerm(Integer.parseInt(filterValues.getTerm()));
            if(!filterValues.getClassType().equals("All"))
                classTypeFilter =  offeringService.retrieveCourseOfferingsClassType(filterValues.getClassType());
            if(!filterValues.getRoomType().equals("All"))
                roomTypeFilter =  offeringService.retrieveCourseOfferingsRoomType(filterValues.getRoomType());
            if(!filterValues.getTimeBlock().equals("All"))
                timeBlockFilter =  offeringService.retrieveCourseOfferingsTimeslot(filterValues.getTimeBlock());
            holder = offeringService.generateSortedArrayListCourseOfferings(2016, 2017, 1);
            //Intersection time
            //Get all arraylists that are greater than 0
            if(!filterValues.getTerm().equals("All"))
                filterHolder.add(termFilter);
            if(!filterValues.getClassType().equals("All"))
                filterHolder.add(classTypeFilter);
            if(!filterValues.getRoomType().equals("All"))
                filterHolder.add(roomTypeFilter);
            if(!filterValues.getTimeBlock().equals("All"))
                filterHolder.add(timeBlockFilter);

            //Intersect all of them
            for(int i = 0; i < filterHolder.size(); i++)
                holder =  offeringService.generateIntersectionLists(holder, filterHolder.get(i));
        }

        offeringService.setFilteredCourses(holder);
        for(CourseOffering cs: holder)
        {
            OfferingModifyDto currDTO;
            currDTO = transferToDTO(cs);
            listOfferDtos.add(currDTO);
        }

        /* Create Response Object */
        Response response = new Response();
        if(listOfferDtos.size() == 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        response.setData(listOfferDtos);
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

    /* Retrieve All Filtered Course Offerings through GET */
    @GetMapping(value = "/get-filtered-offerings")
    public Response retrieveFilteredOfferings(Model model)
    {
        /* Create new list for course offerings */
        Iterator allOfferings = offeringService.getFilteredCourses().iterator();

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

    /* Retrieve All Filtered Course Offerings through GET */
    @GetMapping(value = "/get-filtered-day")
    public Response retrieveDayFilteredOfferings(Model model)
    {
        /* Create new list for course offerings */
        Iterator allOfferings = offeringService.getDayFilteredCourses().iterator();

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

    /* Retrieve All Filtered Course Offerings through GET */
    @GetMapping(value = "/get-filtered-search")
    public Response retrieveSearchOfferings(Model model)
    {
        /* Create new list for course offerings */
        Iterator allOfferings = offeringService.getSearchCourses().iterator();

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
                newDay1.setbeginTime(offering.getStartTime().replace(":", ""));
                newDay1.setendTime(offering.getEndTime().replace(":", ""));
                newDay1.setCourseOffering(currOffering);
                newDay1.setRoom(newRoom);

                daysSet.add(newDay1);
            }

            // If Day 2 is not null or "-" in the form
            if(!(offering.getDay2() == '-'))
            {
                Days newDay2 = new Days();
                newDay2.setclassDay(offering.getDay2());
                newDay2.setbeginTime(offering.getStartTime().replace(":", ""));
                newDay2.setendTime(offering.getEndTime().replace(":", ""));
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
                    dayInstance.setbeginTime(offering.getStartTime().replace(":", ""));
                    dayInstance.setendTime(offering.getEndTime().replace(":", ""));
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
                    dayInstance.setbeginTime(offering.getStartTime().replace(":", ""));
                    dayInstance.setendTime(offering.getEndTime().replace(":", ""));
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
        System.out.println(offering.getFaculty());
        User newFaculty = userService.findUserByFirstNameLastName(offering.getFaculty());
        System.out.println(newFaculty.getLastName());
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

        Set<Days> memories = currOffering.getDaysSet();
        for (Days dayInstance : memories)
        {
            System.out.println("Memories: " + dayInstance.getclassDay() + " " + dayInstance.getbeginTime() + " " + dayInstance.getendTime());
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
        System.out.println("Modification is Happening");
        //System.out.println(offeringDto.getStartTime() + offeringDto.getEndTime());
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
    public ArrayList<OfferingModifyDto> convertToDTO(Iterator allOfferings)
    {
        /* Initialize arraylist */
        ArrayList<OfferingModifyDto> offerings = new ArrayList<>();

        while(allOfferings.hasNext())
        {
            CourseOffering offering = (CourseOffering) allOfferings.next();

            /* Transfer to DTO */
            OfferingModifyDto currDTO = transferToDTO(offering);

            offerings.add(currDTO);
        }

        return offerings;
    }


    public OfferingModifyDto transferToDTO(CourseOffering offering)
    {
        //System.out.println("TRANSFER DTO");
        OfferingModifyDto modifyDto = new OfferingModifyDto();

        /* Offering ID */
        modifyDto.setOfferingId(offering.getofferingId());

        /* Course Code */
        modifyDto.setCourseCode(offering.getCourse().getCourseCode());

        /* Section */
        if (!offering.getSection().equals("") )
            modifyDto.setClassSection(offering.getSection());
        else
            modifyDto.setClassSection("None");

        /* Offering Status/Type */
        if (!offering.getStatus().equals(""))
            modifyDto.setClassStatus(offering.getStatus());
        else
            modifyDto.setClassStatus("Regular");

        /* Offering Faculty */
        if (offering.getFaculty() != null)
            modifyDto.setFaculty(offering.getFaculty().getLastName() + ", " + offering.getFaculty().getFirstName());
        else
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
            if (!day.getbeginTime().equals("") && !day.getendTime().equals("")) {
                if (day.getbeginTime().length() == 3) {
                    modifyDto.setStartTime("0" + day.getbeginTime().charAt(0) + ":" + day.getbeginTime().substring(1, 3));
                    //System.out.println(day.getbeginTime().charAt(0) + ":" + day.getbeginTime().substring(1, 3));
                }
                else if (day.getbeginTime().length() == 4) {
                    modifyDto.setStartTime(day.getbeginTime().substring(0, 2) + ":" + day.getbeginTime().substring(2, 4));
                    //System.out.println(day.getbeginTime().substring(0, 2) + ":" + day.getbeginTime().substring(2, 4));
                }
                if (day.getendTime().length() == 3) {
                    modifyDto.setEndTime("0" + day.getendTime().charAt(0) + ":" + day.getendTime().substring(1, 3));
                    //System.out.println(day.getendTime().charAt(0) + ":" + day.getendTime().substring(1, 3));
                }
                else if (day.getendTime().length() == 4){
                    modifyDto.setEndTime(day.getendTime().substring(0, 2) + ":" + day.getendTime().substring(2, 4));
                    //System.out.println(day.getendTime().substring(0, 2) + ":" + day.getendTime().substring(2, 4));
                }
                //else {
                //    modifyDto.setStartTime(day.getbeginTime());
                //    modifyDto.setEndTime(day.getendTime());
                //}
            }
            else
            {
                modifyDto.setStartTime("00:00");
                modifyDto.setEndTime("00:00");
            }
            day1Done = true;
        }
        if (modifyDto.getDay1() == '\0') {
            modifyDto.setDay1('-');
            modifyDto.setRoomCode("Unassigned");
            modifyDto.setStartTime("00:00");
            modifyDto.setEndTime("00:00");
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

    /* Find all faculty that are available at this timeslot AND are applicable for teaching*/
    @PostMapping(value = "/check-faculty")
    public Response showFacultyApplicable(@RequestBody ModifyRoomDto timeInfo)
    {
        //Get all data to be used for evaluation
        char day1 = timeInfo.getDay1();
        char day2 = timeInfo.getDay2();
        String startTime = timeInfo.getStartTime();
        String endTime = timeInfo.getEndTime();

        ArrayList<FacultyLoad> facultyList = facultyService.facultyRuleChecking(day1, day2, startTime, endTime);
        ArrayList<FacultyLoadDto> transferableFacultyList = new ArrayList<>();

        for(FacultyLoad fl: facultyList)
        {
            FacultyLoadDto currLoad = new FacultyLoadDto();
            currLoad.setAdminLoad(fl.getAdminLoad());
            currLoad.setResearchLoad(fl.getResearchLoad());
            currLoad.setTeachingLoad(fl.getTeachingLoad());
            currLoad.setTotalLoad(fl.getTotalLoad());
            currLoad.setFirstName(fl.getFaculty().getFirstName());
            currLoad.setLastName(fl.getFaculty().getLastName());
            if(!(fl.getTotalLoad() >= 12))
                transferableFacultyList.add(currLoad);
            else
                System.out.println(fl.getFaculty());
        }

        /* Create Response Object*/
        Response response = new Response();
        if(facultyList.size() <= 0)
            response.setStatus("Error");
        else
            response.setStatus("Done");

        response.setData(transferableFacultyList);
        return response;
    }
    /* Retrieve All Concerns through GET */
    @PostMapping(value = "/get-concerns")
    public Response retrieveConcerns(@RequestBody Long userID, Model model)
    {
       // Long converUserID = Long.parseLong(userID);
        System.out.println("User Identifitcation: " + userID);
        /* Create new list for concerns */
        Iterator allConcerns = userService.retrieveAllConcernsByReceiver(userService.findUserByIDNumber(userID));

        /* Convert to DTO */
        ArrayList<ConcernDto> listConcernDtos = new ArrayList<>();
        while(allConcerns.hasNext())
        {
            Concern concern = (Concern) allConcerns.next();
            /* Transfer to DTO */
            ConcernDto conDTO = transferToConcernDTO(concern);

            listConcernDtos.add(conDTO);
        }

        /* Create Response Object */
        Response response = new Response();
        response.setStatus("Done");
        response.setData(listConcernDtos);

        return response;
    }

    public Long findUserSend(String userName)
    {
        Long userID = userService.findUserByFirstNameLastName(userName.replaceAll("^\"|\"$", "")).getUserId();
        System.out.println("find " + userID);
        return userID;
    }

    /* Send and Save a Concern using POST */
    @PostMapping(value = "/post-concern")
    public Response postConcerns(@RequestBody ConcernDto concernSend)
    {
        //convert concernDTO
        System.out.println("Debug supreme");
        Concern concern = this.transferToConcern(concernSend);
        //save concern
        System.out.println("Debug supreme2");
        System.out.println("Before Going in " + concern.getDateTimeCommitted().toString());
        this.userService.saveConcern(concern);
        /* Create Response Object */
        Response response = new Response();
        response.setStatus("Done");
        return response;
    }
    /* Send and Save a Offering using POST */
    @PostMapping(value = "/add-offering")
    public Response addCourse(@RequestBody OfferingModifyDto addedCourse)
    {
        CourseOffering currOffering = new CourseOffering();
        currOffering.setSection(addedCourse.getClassSection());
        currOffering.setStatus(addedCourse.getClassStatus());
        //TODO: checker
        if(!(offeringService.retrieveCourseByCourseCode(addedCourse.getCourseCode().toUpperCase()) == null))
        {
            currOffering.setCourse(offeringService.retrieveCourseByCourseCode(addedCourse.getCourseCode().toUpperCase()));
            Set<Days> daySet = new HashSet<>();
            Days day1 = new Days();
            day1.setbeginTime(addedCourse.getStartTime());
            day1.setendTime(addedCourse.getEndTime());
            day1.setclassDay(addedCourse.getDay1());
            day1.setRoom(offeringService.retrieveRoomByRoomCode(addedCourse.getRoomCode()));
            Days day2 = new Days();
            day2.setbeginTime(addedCourse.getStartTime());
            day2.setendTime(addedCourse.getEndTime());
            day2.setclassDay(addedCourse.getDay2());
            day2.setRoom(offeringService.retrieveRoomByRoomCode(addedCourse.getRoomCode()));
            currOffering.setDaysSet(daySet);
            offeringService.saveCourseOffering(currOffering);
        }

        /* Create Response Object */
        Response response = new Response();
        response.setStatus("Done");
        return response;
    }
     **
     ** FUNCTIONS
     **
     */

    /* Function for converting a list of CourseOfferings to OfferingModifyDto */
    private ArrayList<OfferingModifyDto> convertToDTO(Iterator allOfferings) {
    public ConcernDto transferToConcernDTO(Concern concern)
    {
        LocalDateTime localDateTime = LocalDateTime.now();

        ConcernDto concernDto = new ConcernDto();
        concernDto.setMessage(concern.getMessage());
        concernDto.setUserId(concern.getSender().getUserId());
        concernDto.setSenderName(concern.getSender().getLastName() + ", " + concern.getSender().getFirstName());
        if(concern.getDateTimeCommitted().getYear() == localDateTime.getYear() &&
                concern.getDateTimeCommitted().getMonth() == localDateTime.getMonth() && concern.getDateTimeCommitted().getDayOfMonth() == localDateTime.getDayOfMonth())
        {
            if(concern.getDateTimeCommitted().getHour() < 12)
                concernDto.setDateAdded("Today " + concern.getDateTimeCommitted().getHour() + ":"  + concern.getDateTimeCommitted().getMinute() + " AM");
            else
                concernDto.setDateAdded("Today " + concern.getDateTimeCommitted().getHour() + ":"  + concern.getDateTimeCommitted().getMinute()+ " PM");
        }
        else
        {
            if(concern.getDateTimeCommitted().getHour() < 12)
                concernDto.setDateAdded(concern.getDateTimeCommitted().getMonth() + " " + concern.getDateTimeCommitted().getDayOfMonth() + ", "
                        + concern.getDateTimeCommitted().getYear() + concern.getDateTimeCommitted().getHour() + ":"  + concern.getDateTimeCommitted().getMinute()+ concern.getDateTimeCommitted().getHour() + ":"  + concern.getDateTimeCommitted().getMinute() + " AM");
            else
                concernDto.setDateAdded(concern.getDateTimeCommitted().getMonth() + " " + concern.getDateTimeCommitted().getDayOfMonth() + ", "
                        + concern.getDateTimeCommitted().getYear() + concern.getDateTimeCommitted().getHour() + ":"  + concern.getDateTimeCommitted().getMinute()+ concern.getDateTimeCommitted().getHour() + ":"  + concern.getDateTimeCommitted().getMinute() + " PM");
        }


        System.out.println("Date is " + concernDto.getDateAdded());
        return concernDto;
    }
    public Concern transferToConcern(ConcernDto concernDto)
    {
        Concern concern = new Concern();
        System.out.println(concernDto.getSenderName() + concernDto.getUserId() + concernDto.getMessage());
        concern.setSender(userService.findUserByIDNumber(concernDto.getUserId()));
        System.out.println(concernDto.getSenderName());
        concern.setReceiver(userService.findUserByIDNumber(findUserSend((concernDto.getSenderName()))));
        concern.setMessage(concernDto.getMessage());
        //System.out.println(concern.getTimeDateRecieved());
        System.out.println(concern.getSender().getUserId() + " " + concern.getReceiver().getUserId());
        return concern;
    }
}