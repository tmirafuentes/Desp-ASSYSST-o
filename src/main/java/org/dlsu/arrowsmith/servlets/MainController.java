package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.User;
import org.dlsu.arrowsmith.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private MessageSource messages;

    /***
     *
     *  URL MAPPING
     *
     */

    /* Default Home Page - Login Screen */
    @RequestMapping(value = {"/", "/login", "/welcome", "/index", "/signin"}, method = RequestMethod.GET)
    public String index(Model model, String expired, String error, String logout) {
        if(expired != null)
            model.addAttribute("error", messages.getMessage("message.sessionExpired", null, null));

        if (error != null)
            model.addAttribute("error", messages.getMessage("message.badCredentials", null, null));

        if (logout != null)
            model.addAttribute("message", messages.getMessage("message.logoutSuccess", null, null));

        return "signin";
    }

    /* Default Home Page - Academic Programming Officer Screen */
    @RequestMapping(value = {"/apo", "/apo/home"}, method = RequestMethod.GET)
    public String APOHomePage(Model model) {
        /* Load all course offerings and stuff */
        model.addAttribute("allOfferings", offeringService.retrieveAllOfferings());
        model.addAttribute("allDays", offeringService.generateLetterDays());
        //model.addAttribute("allTerms", offeringService.retrieveAllTermsAndAY());

        return "/apo/apoHome";
    }

    /* Default Home Page - Chairs or Vice Chairs Screen */
    @RequestMapping(value = {"/cvc", "/cvc/home"}, method = RequestMethod.GET)
    public String CVCHomePage(Model model) {
        /* Load all course offerings */
        model.addAttribute("allOfferings", offeringService.retrieveAllOfferings());
        model.addAttribute("allDays", offeringService.generateLetterDays());

        return "/cvc/cvcHome";
    }

    /* Default Home Page - Faculty Screen */
    @RequestMapping(value = {"/faculty", "/faculty/home"}, method = RequestMethod.GET)
    public String FacultyHomePage(Model model) {
        /* Get current user */
        User currFaculty = userService.findUserByIDNumber(Long.valueOf(22734526));

        /* Load all faculty load */
        model.addAttribute("facLoadInfo", facultyService.retrieveFacultyLoadByFaculty(2017, 2018, 2, currFaculty).getTotalLoad());
        model.addAttribute("allTeachingLoads", offeringService.retrieveAllOfferingsByFaculty(currFaculty, 2017, 2018, 2));

        return "facultyHome";
    }
}
