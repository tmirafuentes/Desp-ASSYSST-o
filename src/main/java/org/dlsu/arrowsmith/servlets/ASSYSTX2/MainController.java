package org.dlsu.arrowsmith.servlets.ASSYSTX2;

import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.CreateOfferingDTO;
import org.dlsu.arrowsmith.classes.dtos.ASSYSTX2.SelectDeptDTO;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Iterator;

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
    private FacultyService facultyService;

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

        return "signin-page";
    }

    /* Workspace Page */
    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String WorkspaceHomePage(Model model)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve Current Term */
        model = retrieveCurrentTerm(model);

        /* Test History */
        //userService.retrieveOfferingHistory(offeringService.retrieveCourseOffering(Long.valueOf(241)));

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Context Path */
        model.addAttribute("context", "workspace");

        /* Revision History Test */
        //model.addAttribute("history", userService.findLatestRevisionEntity());

        if (userType.equalsIgnoreCase("APO"))
            return "apo-home-page";
        else if (userType.equalsIgnoreCase("CVC"))
            return "cvc-home-page";

        return "faculty-home-page";
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

        return "concerns-page";
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

        return "workspace-history-page";
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

        /* Retrieve List of Departments */
        Iterator allDepartments = facultyService.retrieveAllDepartmentsByCollege(userService.retrieveUser().getCollege());

        ArrayList<SelectDeptDTO> listDepts = new ArrayList<>();
        while(allDepartments.hasNext())
        {
            Department department = (Department) allDepartments.next();

            SelectDeptDTO dto = new SelectDeptDTO();
            dto.setDeptCode(department.getDeptCode());
            dto.setDeptName(department.getDeptName());

            listDepts.add(dto);
        }
        model.addAttribute("selectDept", listDepts.iterator());

        return "courses-page";
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

        return "faculty-page";
    }

    /* Assign Room Page */
    @PostMapping(value = "/assign-room")
    public ModelAndView AssignRoomPage(Model model, @ModelAttribute("CourseDetails") CreateOfferingDTO dto)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Retrieve and display chosen course offering to modify */
        ModelAndView modelAndView = new ModelAndView("assign-room-page");
        modelAndView.addObject("courseCode", dto.getCourseCode());
        modelAndView.addObject("section", dto.getSection());

        /* OPTIONAL - If it has current days and room assigned, retrieve as well */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(dto.getCourseCode(), dto.getSection());
        Iterator offeringDays = offeringService.retrieveAllDaysByOffering(selectedOffering);
        int dayCtr = 1;
        while(offeringDays.hasNext())
        {
            Days selDays = (Days) offeringDays.next();
            String formatDays = selDays.getRoom().getRoomCode() + " " +
                                selDays.getclassDay() + " " +
                                selDays.getbeginTime() + " - " +
                                selDays.getendTime();
            modelAndView.addObject("day" + dayCtr, formatDays);
            dayCtr++;
        }

        return modelAndView;
    }

    /* Assign Faculty Page */
    @PostMapping(value = "/assign-faculty")
    public ModelAndView AssignFacultyPage(Model model, @ModelAttribute("CourseDetails") CreateOfferingDTO dto)
    {
        /* Retrieve Current User's Full Name */
        model = retrieveLoggedInUser(model);

        /* Retrieve User Type */
        String userType = retrieveUserType();
        model.addAttribute("userType", userType);

        /* Retrieve and display chosen course offering to modify */
        ModelAndView modelAndView = new ModelAndView("assign-faculty-page");
        modelAndView.addObject("courseCode", dto.getCourseCode());
        modelAndView.addObject("section", dto.getSection());

        /* OPTIONAL - If it has current faculty assigned, retrieve as well */
        CourseOffering selectedOffering = offeringService.retrieveOfferingByCourseCodeAndSection(dto.getCourseCode(), dto.getSection());
        User assignedFaculty = selectedOffering.getFaculty();
        if(assignedFaculty != null)
        {
            String facultyName = assignedFaculty.getLastName() + ", " + assignedFaculty.getFirstName();
            modelAndView.addObject("assignedFaculty", facultyName);
        }

        return modelAndView;
    }


    /***
     *
     *  FUNCTIONS
     *
     */

    /* Retrieve currently logged in user */
    protected Model retrieveLoggedInUser(Model model)
    {
        /* Retrieve logged in user */
        User currUser = userService.retrieveUser();

        /* Retrieve name for display */
        String userRealName = currUser.getLastName() + ", " + currUser.getFirstName();
        model.addAttribute("currentUser", userRealName);

        return model;
    }

    /* Retrieve currently logged in user */
    protected String retrieveUserType()
    {
        /* Retrieve logged in user */
        User currUser = userService.retrieveUser();

        /* Retrieve name for display */
        String userType = currUser.getUserType();
        if (userType.equalsIgnoreCase("Academic Programming Officer"))
            return "APO";
        else if (userType.equalsIgnoreCase("Chair") ||
                 userType.equalsIgnoreCase("Vice-Chair"))
            return "CVC";

        return "FACULTY";
    }

    /* Retrieve current term */
    protected Model retrieveCurrentTerm(Model model)
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
