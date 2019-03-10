package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.*;
import org.dlsu.arrowsmith.classes.dtos.FacultyDeloadDto;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.security.SecurityServiceImpl;
import org.dlsu.arrowsmith.services.FacultyService;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class LoadController { // This Controller is for the Faculty Load Assignment Module
    /*** Services ***/
    @Autowired
    private FacultyService facultyService;

    @Autowired
    private UserService userService;

    @Autowired
    private OfferingService offeringService;

    @Autowired
    private SecurityServiceImpl securityService;

    /*** Extra Stuff ***/
    private MessageSource messages;

    /***
     *
     *  ACTIVE
     *  URL MAPPING
     *
     */

    /*** Faculty Load Information Page ***/
    @RequestMapping(value = "/cvc/manage-load", method = RequestMethod.GET)
    public String manageFacultyPage(Model model)
    {
        /* Load logged in user */
        User currUser = userService.retrieveUser();
        String userRealName = currUser.getLastName() + ", " + currUser.getFirstName();
        model.addAttribute("loggedUser", userRealName);

        /* Load Faculty Load Stuff */
        model.addAttribute("allFacultyLoad", facultyService.retrieveAllFacultyLoadByTerm(2016, 2017, 1, currUser.getDepartment()));
        model.addAttribute("uniqueTimeslots", offeringService.getUniqueTimeSlots());
        model.addAttribute("allTerms", offeringService.getUniqueTerms());
        model.addAttribute("allClassTypes", offeringService.generateClassType());
        model.addAttribute("allRoomTypes", offeringService.generateRoomType());
        model.addAttribute("allOfferings", offeringService.retrieveAllOfferingsByTerm(2016, 2017, 1));
        model.addAttribute("allDeloading", facultyService.retrieveAllDeloading());

        /* Load Dto for Modify Faculty Load */
        model.addAttribute("facultyDeloadForm", new FacultyDeloadDto());

        return "cvc/cvcFacultyLoad";
    }

    /*** Deload Faculty ***/
    @RequestMapping(value = {"cvc/deload-faculty"}, method = RequestMethod.POST)
    public String editDeloadModule(@ModelAttribute("facultyDeloadForm") FacultyDeloadDto facultyDeloadDto,
                                   BindingResult bindingResult, HttpServletRequest request, Model model)
    {
        /* Errors */
        String urlPattern = (String) request.getServletPath();
        if (bindingResult.hasErrors())
            return "redirect:/cvc/manage-load";

        /* Else, Deload Faculty */
        /* Retrieve Faculty Load Information of Faculty */
        FacultyLoad facultyLoad = facultyService.retrieveFacultyLoadByID(facultyDeloadDto.getLoadId());

        /* Retrieve Deloading from Database */
        Deloading currDeloading = facultyService.retrieveDeloadingByDeloadCode(facultyDeloadDto.getDeloadType());

        if(checkFacultyLoadDeload(facultyLoad.getFaculty(), facultyLoad.getStartAY(), facultyLoad.getEndAY(), facultyLoad.getTerm()))
        {
            /* Create a Deload Instance */
            DeloadInstance newDeloadInstance = new DeloadInstance();
            newDeloadInstance.setStartAY(facultyLoad.getStartAY());
            newDeloadInstance.setEndAY(facultyLoad.getEndAY());
            newDeloadInstance.setTerm(facultyLoad.getTerm());
            newDeloadInstance.setDeloading(currDeloading);
            newDeloadInstance.setFaculty(facultyLoad.getFaculty());

            /* Modify Faculty Load */
            if(currDeloading.getDeloadType().equals("AL"))      // Admin Load
            {
                facultyLoad.setDeloadedLoad(facultyLoad.getDeloadedLoad() + currDeloading.getUnits());  // Add Units to Faculty Load
                facultyLoad.setAdminLoad(facultyLoad.getAdminLoad() + currDeloading.getUnits());            // Add Units to Admin Load
                facultyLoad.setTeachingLoad(facultyLoad.getTeachingLoad() - currDeloading.getUnits());      // Minus Units to Teach Load
            }
            else if(currDeloading.getDeloadType().equals("RL"))      // Research Load
            {
                facultyLoad.setDeloadedLoad(facultyLoad.getDeloadedLoad() + currDeloading.getUnits());  // Add Units to Faculty Load
                facultyLoad.setResearchLoad(facultyLoad.getResearchLoad() + currDeloading.getUnits());            // Add Units to Admin Load
                facultyLoad.setTeachingLoad(facultyLoad.getTeachingLoad() - currDeloading.getUnits());      // Minus Units to Teach Load
            }

            /* Save Instance and Faculty Load to Database */
            facultyService.saveDeloadInstance(newDeloadInstance);
            facultyService.saveFacultyLoad(facultyLoad);
        }


        return "redirect:/cvc/manage-load";
    }

    public boolean checkFacultyLoadDeload(User faculty, int startAY, int endAY, int term)
    {
        FacultyLoad facultyload = facultyService.retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty);
        if(facultyload.getTotalLoad() <= 0)
            return false;
        return true;
    }

    public boolean checkFacultyLoading(User faculty, int startAY, int endAY, int term, String loadType)
    {
        FacultyLoad facultyload = facultyService.retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty);
        if(facultyload.getTotalLoad() >= 12)
            return false;
        if(loadType.equals("AL") && facultyload.getAdminLoad() >= 6)
            return false;
        return true;
    }
    /***
     *
     *  INACTIVE
     *  URL MAPPING
     *
     */

    /*** Add Faculty ***/
    @RequestMapping(value = "/cvc/manage-load/add", method = RequestMethod.GET)
    public String addNewFacultyPage(Model model)
    {
        /* Get College and Department of Chair */
        User currChair = userService.retrieveUser();
        College currCollege = currChair.getCollege();
        Department currDept = currChair.getDepartment();

        /* Generate Form */
        model.addAttribute("facultyForm", new User());
        model.addAttribute("facultyCollege", currCollege);
        model.addAttribute("facultyDept", currDept);
        return "cvc/add-faculty";
    }

    @RequestMapping(value = "/cvc/manage-load/add", method = RequestMethod.POST)
    public String addNewFacultySubmit(@ModelAttribute("facultyForm") User facultyForm, BindingResult bindingResult, Model model)
    {
        /* Errors */
        if (bindingResult.hasErrors())
            return "/cvc/add-course";

        /* Else, save new faculty to the database */
        userService.createNewUser(facultyForm);

        /* Message that course is successfully updated */
        return "redirect:/cvc/add-faculty";
    }

    /*** Delete Faculty ***/
    @RequestMapping(value = "/cvc/manage-faculty/delete", method = RequestMethod.GET)
    public String deleteCourseSubmit(@RequestParam("facultyID") String facultyID, Model model)
    {
        /* Find Chosen Faculty */
        User currFaculty = userService.findUserByIDNumber(Long.parseLong(facultyID));
        if(currFaculty == null)
            return "redirect:/error";

        /* Delete from database */
        userService.deleteUser(currFaculty);

        /* Message that course is successfully updated */
        return "redirect:/cvc/manage-faculty";
    }
}
