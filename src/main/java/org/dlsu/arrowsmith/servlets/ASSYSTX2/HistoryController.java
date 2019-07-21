package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.RecentChangesDTO;
import org.dlsu.arrowsmith.classes.main.CourseOffering;
import org.dlsu.arrowsmith.classes.main.Response;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.HistoryService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping({"/", "/history"})
public class HistoryController
{
    /*** Services ***/
    @Autowired
    private OfferingService offeringService;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MessageSource messages;

    @GetMapping("/retrieve-recent-changes")
    public Response retrieveMostRecentChanges()
    {
        /* Retrieve all changes */
        Iterator workspaceHistory = historyService.retrieveWorkspaceHistory();

        /* Transfer to new list */
        ArrayList<RecentChangesDTO> mostRecent = new ArrayList<>();
        for(int i = 0; i < 10 && workspaceHistory.hasNext(); i++)
        {
            RecentChangesDTO dto = (RecentChangesDTO) workspaceHistory.next();
            mostRecent.add(dto);
        }

        return new Response("Done", mostRecent.iterator());
    }

    @PostMapping("/retrieve-offering-history")
    public Response retrieveOfferingHistory(@RequestBody String courseOffering)
    {
        /* Clean String */
        courseOffering = courseOffering.substring(0, courseOffering.length() - 1);
        String[] offeringString = courseOffering.split("\\+");

        /* Find Course Offering */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(offeringString[0], offeringString[1]);

        Iterator offeringHistory = historyService.retrieveOfferingHistory(selectedOffering);

        return new Response("Done", offeringHistory);
    }
}