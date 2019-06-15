package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.main.Term;
import org.dlsu.arrowsmith.classes.main.User;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/*
 *  ASSYSTX2
 *  MAIN CONTROLLER
 *
 *  Contents:
 *  URL Mappings for the main web pages
 *  of the ASSYSTX2 System: Workspace,
 *  Concerns, History, Courses, Faculty.
 */

@Controller
public class MainController
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
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String SignInPage(Model model, String expired, String error, String logout)
    {
        if(expired != null)
            model.addAttribute("error", messages.getMessage("message.sessionExpired", null, null));

        if (error != null)
            model.addAttribute("error", messages.getMessage("message.badCredentials", null, null));

        if (logout != null)
            model.addAttribute("message", messages.getMessage("message.logoutSuccess", null, null));

        return "assystx2/general-screens/signin";
    }

    /* Workspace Page */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String WorkspaceHomePage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve Current Term */
        model = retrieveCurrentTerm(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Context Path */
        model.addAttribute("context", "workspace");

        if (userType.equalsIgnoreCase("APO"))
            return "/assystx2/apo-screens/apo-home";
        else if (userType.equalsIgnoreCase("CVC"))
            return "/assystx2/cvc-screens/cvc-home";

        return "/assystx2/general-screens/faculty-home";
    }

    @RequestMapping(value = "/concerns", method = RequestMethod.GET)
    public String ConcernsHomePage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve Current Term */
        model = retrieveCurrentTerm(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Context Path */
        model.addAttribute("context", "concerns");

        return "/assystx2/general-screens/concerns";
    }

    /* Workspace History Page */
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String RevisionHistoryPage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve Current Term */
        model = retrieveCurrentTerm(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Context Path */
        model.addAttribute("context", "history");

        return "/assystx2/general-screens/history";
    }

    /* Course Management Page */
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String CourseManagementPage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve Current Term */
        model = retrieveCurrentTerm(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Context Path */
        model.addAttribute("context", "courses");

        return "/assystx2/general-screens/courses-page";
    }

    /* Workspace History Page */
    @RequestMapping(value = "/faculty", method = RequestMethod.GET)
    public String FacultyManagementPage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve Current Term */
        model = retrieveCurrentTerm(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Context Path */
        model.addAttribute("context", "faculty");

        return "/assystx2/general-screens/faculty-page";
    }

    /***
     *
     *  FUNCTIONS
     *
     */

    /* Retrieve currently logged in user */
    private Model retrieveLoggedInUser(Model model)
    {
        /* Retrieve logged in user */
        User currUser = userService.retrieveUser();

        /* Retrieve name for display */
        String userRealName = currUser.getLastName() + ", " + currUser.getFirstName();
        model.addAttribute("currentUser", userRealName);

        return model;
    }

    /* Retrieve currently logged in user */
    private String retrieveUserType()
    {
        /* Retrieve logged in user */
        User currUser = userService.retrieveUser();

        /* Retrieve name for display */
        String userType = currUser.getUserType();
        if (userType.equalsIgnoreCase("Academic Programming Officer"))
            return "APO";
        else if (userType.equalsIgnoreCase("Chair") ||
                 userType.equalsIgnoreCase("Vice Chair"))
            return "CVC";

        return "FACULTY";
    }

    /* Retrieve current term */
    private Model retrieveCurrentTerm(Model model)
    {
        /* Retrieve term */
        Term currTerm = userService.retrieveCurrentTerm();

        /* Format string */
        String term = "A.Y. " + currTerm.getStartAY() + " - " + currTerm.getEndAY() + ": Term " + currTerm.getTerm();

        /* Add to Model */
        model.addAttribute("termString", term);

        return model;
    }
}
