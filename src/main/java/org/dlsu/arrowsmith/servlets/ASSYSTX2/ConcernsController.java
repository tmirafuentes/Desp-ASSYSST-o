package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.main.Response;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.SendConcernDTO;
import org.dlsu.arrowsmith.classes.main.*;
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
        if(currentUser.getUserType().equals("Academic Programming Officer"))
        {
            /* Retrieve department */
            Department department = selectedCourse.getDepartment();

            User deptHead = userService.retrieveDepartmentHead(department, "Vice-Chair");

            return new Response("Done", deptHead.getLastName() + ", " + deptHead.getFirstName());
        }
        else if(currentUser.getUserType().equals("Chair") || currentUser.getUserType().equals("Vice-Chair"))
        {
            /* Retrieve college */
            College college = selectedCourse.getCollege();

            User acadAssistant = userService.retrieveAcadAssistant(college);

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
        userService.saveConcern(newConcern);

        return new Response("Done", messages.getMessage("message.sendConcern", null, null));
    }

    @GetMapping(value = "/retrieve-recent-concerns")
    public Response retrieveRecentConcerns()
    {
        /* Retrieve current user */
        User currentUser = userService.retrieveUser();

        /* Retrieve partial concerns */
        Iterator partialConcerns = userService.retrievePartialConcernsByReceiver(currentUser);

        /* Convert to DTO */
        ArrayList<SendConcernDTO> dtos = new ArrayList<>();
        int ctr = 0;
        while(partialConcerns.hasNext() && ctr < 5)
        {
            /* Get concern */
            Concern concern = (Concern) partialConcerns.next();

            /* Get sender */
            String sender = concern.getSender().getLastName() + ", " + concern.getSender().getFirstName();

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

            dtos.add(dto);

            ctr++;
        }

        return new Response("Done", dtos.iterator());
    }

    @GetMapping(value = "/mark-all-recent-concerns")
    public Response markAllRecentConcerns()
    {
        /* Retrieve current user */
        User currentUser = userService.retrieveUser();

        /* Retrieve partial concerns */
        Iterator partialConcerns = userService.retrievePartialConcernsByReceiver(currentUser);

        /* Mark all partial concerns as acknowledged */
        while(partialConcerns.hasNext())
        {
            Concern selectedConcern = (Concern) partialConcerns.next();

            selectedConcern.setAcknowledged(true);
            userService.saveConcern(selectedConcern);
        }

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
            allConcerns = userService.retrieveAllConcernsByReceiver(currentUser);
        else if(type.equals("sent"))
            allConcerns = userService.retrieveAllConcernsBySender(currentUser);

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

            dtos.add(dto);
        }

        return new Response("Done", dtos.iterator());
    }

    @PostMapping(value = "/mark-acknowledged-concern")
    public Response markAcknowledgedConcern(@RequestBody String id)
    {
        id = id.substring(0, id.length() - 1);

        Concern concern = userService.findConcernByConcernId(Long.parseLong(id));
        concern.setAcknowledged(true);
        userService.saveConcern(concern);

        return new Response("Done", null);
    }
}

