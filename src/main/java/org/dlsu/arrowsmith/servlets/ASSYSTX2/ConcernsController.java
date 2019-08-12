package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.main.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.SendConcernDTO;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.ConcernsService;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping({"/", "/concerns", "/history", "/courses", "/faculty", "/assign-room", "/assign-faculty"})
public class ConcernsController
{
    /*** Services ***/
    @Autowired
    private OfferingService offeringService;

    @Autowired
    private UserService userService;

    @Autowired
    private ConcernsService concernsService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private MessageSource messages;

    /* This function retrieves the appropriate receiver of a concern */
    @PostMapping(value = "/retrieve-concerns-receiver")
    public Response retrieveConcernsReceiver(@RequestBody String courseCode)
    {
        courseCode = courseCode.substring(0, courseCode.length() - 1);

        /* Retrieve sender first */
        User currentUser = userService.retrieveUser();

        /* Retrieve course */
        Course selectedCourse = offeringService.retrieveCourseByCourseCode(courseCode);

        /* If sender is APO, he/she must send to a chair */
        if(currentUser.getUserType().equals("Academic Programming Officer") ||
           currentUser.getUserType().equals("Faculty"))
        {
            /* Retrieve department */
            Department department = selectedCourse.getDepartment();

            User deptHead = concernsService.retrieveDepartmentHead(department, "Vice-Chair");

            return new Response("Done", deptHead.getLastName() + ", " + deptHead.getFirstName());
        }
        else if(currentUser.getUserType().equals("Chair") || currentUser.getUserType().equals("Vice-Chair"))
        {
            /* Retrieve college */
            College college = selectedCourse.getCollege();

            User acadAssistant = concernsService.retrieveAcadAssistant(college);

            return new Response("Done", acadAssistant.getLastName() + ", " + acadAssistant.getFirstName());
        }

        return new Response("Not Done", null);
    }

    @PostMapping(value = "/send-concern")
    public Response sendConcern(@RequestBody SendConcernDTO concern)
    {
        /* Find receiver */
        User receiver = userService.findUserByFirstNameLastName(concern.getReceiver());

        /* Create Concerns */
        Concern newConcern = new Concern();
        newConcern.setSender(userService.retrieveUser());
        newConcern.setReceiver(receiver);
        newConcern.setMessage(concern.getContent());
        newConcern.setSubject(concern.getSubject());
        newConcern.setDateTimeCommitted(LocalDateTime.now());

        /* Send Concern */
        Concern savedConcern = concernsService.saveConcern(newConcern);

        /* Update User Activity */
        UserActivity userActivity = userService.retrieveUserActivity(receiver);
        userActivity.setConcernNotified(false);
        userService.saveUserActivity(userActivity);

        return new Response("Done", messages.getMessage("message.sendConcern", null, null));
    }

    @GetMapping(value = "/retrieve-recent-concerns")
    public Response retrieveRecentConcerns()
    {
        /* Retrieve current user */
        User currentUser = userService.retrieveUser();

        /* Retrieve partial concerns */
        Iterator partialConcerns = concernsService.retrievePartialConcernsByReceiver(currentUser);

        /* Convert to DTO */
        ArrayList<SendConcernDTO> dtos = new ArrayList<>();
        int ctr = 0;
        while(partialConcerns.hasNext() && ctr < 5)
        {
            /* Get concern */
            Concern concern = (Concern) partialConcerns.next();

            /* Get sender */
            String sender = concern.getSender().getLastName() + ", " + concern.getSender().getFirstName();

            dtos.add(0, createSendConcernDTO(concern, sender));

            ctr++;
        }

        /* Show Notification or not */
        UserActivity userActivity = userService.retrieveUserActivity(currentUser);
        boolean concernsNotification = false;
        if(userActivity.isConcernNotified())
            concernsNotification = true;

        return new Response("Done", dtos.iterator(), concernsNotification);
    }

    @GetMapping(value = "/update-recent-concerns")
    public Response updateRecentConcerns()
    {
        UserActivity userActivity = userService.retrieveUserActivity(userService.retrieveUser());
        Response response = retrieveRecentConcerns();

        Long concernID = null;
        Iterator data = (Iterator) response.getData();
        while(data.hasNext())
        {
            SendConcernDTO concern = (SendConcernDTO) data.next();
            concernID = concern.getId();
        }

        //if(concernID == userActivity.getLastConcern().longValue())
            //return new Response("No Change", null, response.getMessage());

        return retrieveRecentConcerns();
    }

    @PostMapping(value = "/mark-acknowledged-concern")
    public Response markAcknowledgedConcern(@RequestBody String id)
    {
        //id = id.substring(0, id.length() - 1);

        Concern concern = concernsService.findConcernByConcernId(Long.parseLong(id));
        concern.setAcknowledged(true);
        concernsService.saveConcern(concern);

        /* Update User Activity */
        UserActivity userActivity = userService.retrieveUserActivity(concern.getReceiver());
        userActivity.setLastConcernSeen(concern.getconcernId());
        userService.saveUserActivity(userActivity);

        return new Response("Done", null);
    }

    @GetMapping(value = "/mark-all-recent-concerns")
    public Response markAllRecentConcerns()
    {
        /* Retrieve current user */
        User currentUser = userService.retrieveUser();

        /* Retrieve partial concerns */
        Iterator partialConcerns = concernsService.retrievePartialConcernsByReceiver(currentUser);

        /* Update User Activity */
        UserActivity userActivity = userService.retrieveUserActivity(currentUser);

        /* Mark all partial concerns as acknowledged */
        while(partialConcerns.hasNext())
        {
            Concern selectedConcern = (Concern) partialConcerns.next();

            selectedConcern.setAcknowledged(true);
            concernsService.saveConcern(selectedConcern);

            userActivity.setLastConcernSeen(selectedConcern.getconcernId());
        }
        userService.saveUserActivity(userActivity);

        return new Response("Done", null);
    }

    @PostMapping(value = "/retrieve-concerns-list")
    public Response retrieveConcernsList(@RequestBody String type)
    {
        /* Get type */
        type = type.substring(0, type.length() - 1);

        /* Get User */
        User currentUser = userService.retrieveUser();

        Iterator allConcerns = null;

        /* Received Concerns */
        if(type.equals("inbox"))
            allConcerns = concernsService.retrieveAllConcernsByReceiver(currentUser);
        else if(type.equals("sent"))
            allConcerns = concernsService.retrieveAllConcernsBySender(currentUser);

        /* Transfer to DTO */
        ArrayList<SendConcernDTO> dtos = new ArrayList<>();
        while(allConcerns.hasNext())
        {
            Concern concern = (Concern) allConcerns.next();

            String sender = "";
            if(type.equals("inbox"))
                sender += concern.getSender().getLastName() + ", " + concern.getSender().getFirstName();
            else
                sender += "To: " + concern.getReceiver().getLastName() + ", " + concern.getReceiver().getFirstName();

            dtos.add(createSendConcernDTO(concern, sender));
        }

        return new Response("Done", dtos.iterator());
    }

    @PostMapping(value = "/disable-concern-notifs")
    public Response disableConcernNotifs()
    {
        User currentUser = userService.retrieveUser();
        UserActivity userActivity = userService.retrieveUserActivity(currentUser);
        userActivity.setConcernNotified(true);
        userService.saveUserActivity(userActivity);

        return new Response("Done", null);
    }

    private SendConcernDTO createSendConcernDTO(Concern concern, String sender)
    {
        /* Get timestamp */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
        String timestamp = concern.getDateTimeCommitted().format(formatter);

        /* Form DTO */
        SendConcernDTO dto = new SendConcernDTO();
        dto.setId(concern.getconcernId());
        dto.setContent(concern.getMessage());
        dto.setSender(sender);
        dto.setSubject(concern.getSubject());
        dto.setAcknowledged(concern.isAcknowledged());
        dto.setTimestamp(timestamp);

        return dto;
    }
}

