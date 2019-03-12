package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.Course;
import org.dlsu.arrowsmith.classes.Role;
import org.dlsu.arrowsmith.classes.User;
import org.dlsu.arrowsmith.classes.dtos.FacultyDeloadDto;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.security.SecurityServiceImpl;
import org.dlsu.arrowsmith.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Iterator;

@Controller
public class MainController {
    /* All Services */
    @Autowired
    private UserService userService;

    @Autowired
    private OfferingService offeringService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private MessageSource messages;

    /***
     *
     *  URL MAPPING
     *
     */

    /* Default Home Page - Login Screen */
    @RequestMapping(value = {"/", "/index", "/signin"}, method = RequestMethod.GET)
    public String index(Model model, String expired, String error, String logout) {
        if(expired != null)
            model.addAttribute("error", messages.getMessage("message.sessionExpired", null, null));

        if (error != null)
            model.addAttribute("error", messages.getMessage("message.badCredentials", null, null));

        if (logout != null)
            model.addAttribute("message", messages.getMessage("message.logoutSuccess", null, null));

        return "user/signin";
    }

    /* Default Home Page - Academic Programming Officer Screen */
    @RequestMapping(value = {"/apo", "/apo/home"}, method = RequestMethod.GET)
    public String APOHomePage(Model model)
    {
        /* Load all Course Offerings, User, and other Stuff */
        model = loadAttributes(model);

        /* Load Rooms for Room Assignment */
        model.addAttribute("allRooms", offeringService.retrieveAllRooms());

        /* Load Dto for Modify Course Offering */
        model.addAttribute("offerModifyForm", new OfferingModifyDto());

        /* Load Object for Add Course Offering */
        model.addAttribute("addOfferingForm", new Course());

        return "/apo/apoHome";
    }

    /* Default Home Page - Chairs or Vice Chairs Screen */
    @RequestMapping(value = {"/cvc", "/cvc/home"}, method = RequestMethod.GET)
    public String CVCHomePage(Model model)
    {
        /* Load all Course Offerings, User, and other Stuff */
        model = loadAttributes(model);

        /* Load Faculty Load for Faculty Load Assignment */
        model.addAttribute("allFacultyLoad", facultyService.retrieveAllFacultyLoadByTerm(2016, 2017, 1,
                                                                                            userService.retrieveUser().getDepartment()));

        /* Load Dto for Modify Course Offering */
        model.addAttribute("offerModifyForm", new OfferingModifyDto());

        return "/cvc/cvcHome";
    }

    /* Default Home Page - Faculty Screen */
    @RequestMapping(value = {"/faculty", "/faculty/home"}, method = RequestMethod.GET)
    public String FacultyHomePage(Model model)
    {
        /* Load logged in user */
        User currUser = userService.retrieveUser();
        String userRealName = currUser.getLastName() + ", " + currUser.getFirstName();
        model.addAttribute("loggedUser", userRealName);

        /* Load all faculty load */
        model.addAttribute("facLoadInfo", facultyService.retrieveFacultyLoadByFaculty(2016, 2017, 1, currUser));
        model.addAttribute("allTeachingLoads", offeringService.retrieveAllOfferingsByFaculty(currUser, 2016, 2017, 1));

        return "/faculty/facultyHome";
    }

    /* Load Collaborative Workspace Information */
    private Model loadAttributes(Model model) {
        /* Load logged in user */
        User currUser = userService.retrieveUser();
        String userRealName = currUser.getLastName() + ", " + currUser.getFirstName();
        model.addAttribute("loggedUser", userRealName);

        /* Load all stuff */
        model.addAttribute("allOfferings", offeringService.generateSortedCourseOfferings(2016, 2017, 1));
        model.addAttribute("allDays", offeringService.generateLetterDays());
        model.addAttribute("allRoomTypes", offeringService.generateRoomType());
        model.addAttribute("allCourses", offeringService.retrieveAllCourses());
        model.addAttribute("allDegrees", offeringService.retrieveAllDegreePrograms());
        model.addAttribute("allTimeslots", offeringService.getUniqueTimeSlots());
        model.addAttribute("allTerms", offeringService.getUniqueTerms());
        model.addAttribute("allClassTypes", offeringService.generateClassType());
        //model.addAttribute("allBuildings", offeringService.retrieveAllBuildings());
        //model.addAttribute("allRoomsTypesModal", offeringService.generateRoomType());

        return model;
    }

}
