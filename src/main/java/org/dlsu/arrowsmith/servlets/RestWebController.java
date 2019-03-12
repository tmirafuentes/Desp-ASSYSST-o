package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.*;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                newDay1.setbeginTime(offering.getStartTimeParsed());
                newDay1.setendTime(offering.getEndTimeParsed());
                newDay1.setCourseOffering(currOffering);
                newDay1.setRoom(newRoom);

                daysSet.add(newDay1);
            }

            // If Day 2 is not null or "-" in the form
            if(!(offering.getDay2() == '-'))
            {
                Days newDay2 = new Days();
                newDay2.setclassDay(offering.getDay2());
                newDay2.setbeginTime(offering.getStartTimeParsed());
                newDay2.setendTime(offering.getEndTimeParsed());
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
                    dayInstance.setbeginTime(offering.getStartTimeParsed());
                    dayInstance.setendTime(offering.getEndTimeParsed());
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
                    dayInstance.setbeginTime(offering.getStartTimeParsed());
                    dayInstance.setendTime(offering.getEndTimeParsed());
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

        offeringService.saveCourseOffering(currOffering);

        /* Create Response Object */
        Response response = new Response();
        response.setStatus("Done");
        //response.setData(currOffering);

        System.out.println("I reached it here");
        return response;
    }
}
