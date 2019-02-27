package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.*;
import org.dlsu.arrowsmith.classes.dtos.FacultyLoadModifyDto;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
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
    /*** Extra Stuff ***/
    private MessageSource messages;

    /***
     *
     *  URL MAPPING
     *
     */

    /*** Faculty Load Assignment Modal ***/
    @RequestMapping(value = "/cvc/manage-load", method = RequestMethod.GET)
    public String manageFacultyPage(Model model)
    {
        model.addAttribute("allFacultyLoad", facultyService.retrieveAllFacultyLoadByTerm(2016, 2017, 1));
        model.addAttribute("uniqueTimeslots", offeringService.getUniqueTimeSlots());
        model.addAttribute("allTerms", offeringService.getUniqueTerms());
        model.addAttribute("allClassTypes", offeringService.generateClassType());
        model.addAttribute("allRoomTypes", offeringService.generateRoomType());
        model.addAttribute("allOfferings", offeringService.retrieveAllOfferingsByTerm(2016, 2017, 1));
        model.addAttribute("alldeloadInstances", facultyService.retrieveFacultyDeloadings());
        model.addAttribute("addOfferingForm", new Course());
        /* Load Dto for Modify Faculty Load Offering */

        model.addAttribute("facultyloadModifyForm", new FacultyLoadModifyDto());
        return "cvc/cvcFacultyLoad";
    }

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

    @RequestMapping(value = {"cvc/manage-load"}, method = RequestMethod.POST)
    public String editDeloadModule(@ModelAttribute("facultyloadModifyForm") FacultyLoadModifyDto facultyloadDto,
                                   BindingResult bindingResult, HttpServletRequest request, Model model)
    {
        /* Errors */
        String urlPattern = (String) request.getServletPath();
        if (bindingResult.hasErrors())
            return "cvc/manage-load";

        System.out.println("Hello  = " + facultyloadDto.toString());

        /* Else, save the old facultyload with new stuff inside */
        FacultyLoad facultyLoad = facultyService.retrieveFacultyLoadByID(facultyloadDto.getLoadId()); // Offering Id
        String deloadType = facultyloadDto.getDeloadType();
        int unitstoDeload = facultyloadDto.getDeloadUnits();
        System.out.println(deloadType + " " + unitstoDeload);
        System.out.println("Current Faculty Load:  " + facultyLoad.getloadId());
        if(deloadType.equals("Administrative") && unitstoDeload <= facultyLoad.getAdminLoad()) {
            facultyLoad.setAdminLoad(facultyLoad.getAdminLoad() - unitstoDeload); // Faculty Admin Load
        }
        else if(deloadType.equals("Research") && unitstoDeload <= facultyLoad.getResearchLoad()) {
            facultyLoad.setAdminLoad(facultyLoad.getResearchLoad() - unitstoDeload); // Faculty Research Load
        }
            else if(deloadType.equals("Teaching") && unitstoDeload <= facultyLoad.getTeachingLoad()) {
            facultyLoad.setAdminLoad(facultyLoad.getTeachingLoad() - unitstoDeload);// Faculty Teaching Load
        }
        // Save it to the database
        facultyService.saveFacultyLoad(facultyLoad);

        /* Message that course is successfully updated */
        if (urlPattern.contains("apo"))
            return "redirect:/apo/manage-load";
        else if (urlPattern.contains("cvc"))
            return "redirect:/cvc";

        return "redirect:/error";
    }

}
