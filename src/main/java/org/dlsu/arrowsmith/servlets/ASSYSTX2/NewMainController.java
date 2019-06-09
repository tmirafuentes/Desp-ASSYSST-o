package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.classes.main.Course;
import org.dlsu.arrowsmith.classes.main.User;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NewMainController
{
    @Autowired
    private UserService userService;

    @Autowired
    private OfferingService offeringService;

    @Autowired
    private MessageSource messages;

    /***
     *
     *  URL MAPPING
     *
     */

    /* Default Home Page - Login Screen */
    @RequestMapping(value = {"/", "/signin", "/assystx2/signin"}, method = RequestMethod.GET)
    public String index(Model model, String expired, String error, String logout)
    {
        if(expired != null)
            model.addAttribute("error", messages.getMessage("message.sessionExpired", null, null));

        if (error != null)
            model.addAttribute("error", messages.getMessage("message.badCredentials", null, null));

        if (logout != null)
            model.addAttribute("message", messages.getMessage("message.logoutSuccess", null, null));

        return "assystx2/general-screens/signin";
    }

    /* Default Home Page - Academic Programming Officer Screen */
    @RequestMapping(value = {"/assystx2/apo", "/assystx2/apo/home"}, method = RequestMethod.GET)
    public String APOHomePage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Load 15 Course Offerings */
        //model.addAttribute("course-offerings", offeringService.retrievePartialCourseOfferings(2016, 2017, 1));

        /* Load Data Transfer Object for Create New Course Offering */
        model.addAttribute("create-offering-dto", new Course());

        return "/assystx2/apo-screens/apo-home";
    }

    /* Default Home Page - Chairs or Vice Chairs Screen */
    @RequestMapping(value = {"/assystx2/cvc", "/assystx2/cvc/home"}, method = RequestMethod.GET)
    public String CVCHomePage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Load 15 Course Offerings */
        //model.addAttribute("course-offerings", offeringService.retrievePartialCourseOfferings(2016, 2017, 1));

        /* Load Data Transfer Object for Create New Course Offering */
        model.addAttribute("create-offering-dto", new Course());

        return "/assystx2/cvc-screens/cvc-home";
    }

    /* Default Home Page - Faculty Screen */
    @RequestMapping(value = {"/assystx2/faculty", "/assystx2/faculty/home"}, method = RequestMethod.GET)
    public String FacultyHomePage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        return "/assystx2/general-screens/faculty-home";
    }

    /* Default Revision History Page */
    @RequestMapping(value = "/assystx2/revision-history", method = RequestMethod.GET)
    public String RevisionHistoryPage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        return "/assystx2/general-screens/revision-history";
    }

    /***
     *
     *  FUNCTIONS
     *
     */

    /* Retrieve currently logged in user */
    private Model retrieveLoggedInUser(Model model)
    {
        User currUser = userService.retrieveUser();
        String userRealName = currUser.getLastName() + ", " + currUser.getFirstName();
        model.addAttribute("current-user", userRealName);

        return model;
    }
}
