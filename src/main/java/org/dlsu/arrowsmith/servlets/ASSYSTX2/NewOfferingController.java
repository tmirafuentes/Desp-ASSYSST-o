package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dro.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.AssignRoomDTO;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.CreateOfferingDTO;
import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.Days;
import org.dlsu.arrowsmith.classes.main.Room;
import org.dlsu.arrowsmith.services.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping({"/assystx2", "/assystx2/apo", "/assystx2/cvc"})
public class NewOfferingController
{
    @Autowired
    private OfferingService offeringService;

    /*
     *
     *  URL MAPPING
     *
     */

    @PostMapping(value = "/assign-room")
    public String AssignRoomPage(Model model, @ModelAttribute("CourseDetails") CreateOfferingDTO dto)
    {
        model.addAttribute("courseCode", dto.getCourseCode());
        model.addAttribute("section", dto.getSection());

        return "/assystx2/apo-screens/apo-assign-room";
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
