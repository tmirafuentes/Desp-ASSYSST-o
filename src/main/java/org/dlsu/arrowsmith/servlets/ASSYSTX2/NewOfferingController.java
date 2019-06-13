package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.*;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/*
 *  ASSYSTX2
 *  WORKSPACE TASKS
 *  FOR OFFERINGS CONTROLLER
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
public class NewOfferingController
{
    @Autowired
    private OfferingService offeringService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserService userService;

    /*
     *  WEB PAGE REQUESTS
     *  URL MAPPING
     *
     */

    @PostMapping(value = "/assign-room")
    public ModelAndView AssignRoomPage(Model model, @ModelAttribute("CourseDetails") CreateOfferingDTO dto)
    {
        /* Retrieve and display chosen course offering to modify */
        ModelAndView modelAndView = new ModelAndView("/assystx2/apo-screens/apo-assign-room");
        modelAndView.addObject("courseCode", dto.getCourseCode());
        modelAndView.addObject("section", dto.getSection());

        return modelAndView;
    }

    @PostMapping(value = "/assign-faculty")
    public ModelAndView AssignFacultyPage(Model model, @ModelAttribute("CourseDetails") CreateOfferingDTO dto)
    {
        /* Retrieve and display chosen course offering to modify */
        ModelAndView modelAndView = new ModelAndView("/assystx2/cvc-screens/cvc-assign-faculty");
        modelAndView.addObject("courseCode", dto.getCourseCode());
        modelAndView.addObject("section", dto.getSection());

        return modelAndView;
    }

    /*
     *  REST REQUESTS
     *  URL MAPPING
     */

    /* Update the selected course offering's room assignment */
    @PostMapping(value = "/update-offering-room")
    public Response updateCourseOfferingRoom(@RequestBody AssignRoomDTO dto)
    {
        /* Retrieve course offering from database */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(dto.getOfferingID());

        /* Find Room Object */
        Room newRoom = offeringService.retrieveRoomByRoomCode(dto.getRoomCode());

        /* Update Days Object */
        Iterator daysList = offeringService.retrieveAllDaysByOffering(selectedOffering);

        /* No current class days and room for the offering */
        if (daysList == null)
        {
            /* Save First Day */
            Days day1 = transferAssignRoomDTOToDays(dto, newRoom, selectedOffering, 1);
            offeringService.saveDays(day1);

            /* OPTIONAL - Save Second Day */
            if (dto.getDay2() != '-')
            {
                Days day2 = transferAssignRoomDTOToDays(dto, newRoom, selectedOffering, 2);
                offeringService.saveDays(day2);
            }
        }
        /* There is already an assigned days for the offering */
        else
        {
            boolean isDay1Done = false;
            while(daysList.hasNext())
            {
                Days dayInstance = (Days) daysList.next();

                // If input Day 1 is not null or "-" in the form - update day instance
                if (!isDay1Done)
                {
                    updateDaysInstance(dayInstance, dto, newRoom, selectedOffering, 1);
                    isDay1Done = true;
                    continue;
                }

                // If Day 2 is not null
                if (dto.getDay2() != '-' && isDay1Done)
                    updateDaysInstance(dayInstance, dto, newRoom, selectedOffering, 2);
                // If Day 2 is null
                else if (dto.getDay2() == '-' && isDay1Done)
                    offeringService.deleteSpecificDay(dayInstance);
            }
        }

        return new Response("Done", null);
    }

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

    /*
     *
     *  FUNCTIONS
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
}
