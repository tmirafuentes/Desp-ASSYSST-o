package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    /***
     *
     *  ACTIVE
     *  URL MAPPING
     *
     */

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
            if (day.getbeginTime() != null && day.getendTime() != null)
            {
                modifyDto.setStartTime(day.getbeginTime());
                modifyDto.setEndTime(day.getendTime());
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
}
