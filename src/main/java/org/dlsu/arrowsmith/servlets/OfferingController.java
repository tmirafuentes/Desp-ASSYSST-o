package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.Course;
import org.dlsu.arrowsmith.classes.CourseOffering;
import org.dlsu.arrowsmith.classes.Days;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.services.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OfferingController {   // This Controller is for the Course Scheduling Module
    /*** Services ***/
    @Autowired
    private OfferingService offeringService;

    /*** Extra Stuff ***/
    private MessageSource messages;

    /***
     *
     *  URL MAPPING
     *
     */

    /*** Course Management Modal ***/
    @RequestMapping(value = "/cvc/manage-course", method = RequestMethod.GET)
    public String manageCoursesPage(Model model)
    {
        model.addAttribute("allCourses", offeringService.retrieveAllCourses());

        return "cvc/manage-course";
    }

    /*** Add Course ***/
    @RequestMapping(value = "/cvc/manage-course/add", method = RequestMethod.GET)
    public String addNewCoursePage(Model model)
    {
        model.addAttribute("courseForm", new Course());
        model.addAttribute("courseColleges", offeringService.retrieveAllColleges());
        model.addAttribute("courseDepts", offeringService.retrieveAllDepartments());

        return "cvc/add-course";
    }

    @RequestMapping(value = "/cvc/manage-course/add", method = RequestMethod.POST)
    public String addNewCourseSubmit(@ModelAttribute("courseForm") Course courseForm, BindingResult bindingResult, Model model)
    {
        /* Errors */
        if (bindingResult.hasErrors())
            return "/cvc/add-course";

        /* Else, save new course to the database */
        offeringService.saveCourse(courseForm);

        /* Message that course is successfully updated */
        return "redirect:/cvc/add-course";
    }

    /*** Edit Course ***/
    @RequestMapping(value = "/cvc/manage-course/edit", method = RequestMethod.GET)
    public String editCoursePage(@RequestParam("courseID") String courseCode, Model model)
    {
        /* Find Specific Course */
        Course currCourse = offeringService.retrieveCourseByCourseCode(courseCode);
        if(currCourse == null)
            return "redirect:/error";

        model.addAttribute("editedCourse", currCourse);
        model.addAttribute("courseForm", new Course());
        model.addAttribute("courseColleges", offeringService.retrieveAllColleges());
        model.addAttribute("courseDepts", offeringService.retrieveAllDepartments());

        return "cvc/edit-course";
    }

    @RequestMapping(value = "/cvc/manage-course/edit", method = RequestMethod.POST)
    public String editCourseSubmit(@ModelAttribute("courseForm") Course courseForm, BindingResult bindingResult, Model model)
    {
        /* Errors */
        if (bindingResult.hasErrors())
            return "/cvc/edit-course";

        /* Else, save new course to the database */
        offeringService.saveCourse(courseForm);

        /* Message that course is successfully updated */
        return "redirect:/cvc/manage-course";
    }

    /*** Delete Course ***/
    @RequestMapping(value = "/cvc/manage-course/delete", method = RequestMethod.GET)
    public String deleteCourseSubmit(@RequestParam("courseID") String courseCode, Model model)
    {
        /* Find Chosen Course */
        Course currCourse = offeringService.retrieveCourseByCourseCode(courseCode);
        if(currCourse == null)
            return "redirect:/error";

        /* Delete from database */
        offeringService.deleteCourse(currCourse);

        /* Message that course is successfully updated */
        return "redirect:/cvc/manage-course";
    }

    /*** Add Course Offering ***/
    @RequestMapping(value = "/apo/manage-offering/add", method = RequestMethod.GET)
    public String addNewOfferingPage(Model model)
    {
        model.addAttribute("offeringForm", new CourseOffering());
        model.addAttribute("allCourses", offeringService.retrieveAllCourses());

        return "apo/add-offering";
    }

    @RequestMapping(value = "/apo/manage-offering/add", method = RequestMethod.POST)
    public String addNewOfferingSubmit(@ModelAttribute("offeringForm") CourseOffering offeringForm, BindingResult bindingResult, Model model)
    {
        /* Errors */
        if (bindingResult.hasErrors())
            return "/apo/add-offering";

        /* Else, save new course offering to the database */
        offeringService.saveCourseOffering(offeringForm);

        /* Message that course is successfully updated */
        return "redirect:/apo/add-offering";
    }

    /*** Update Course Offering ***/
    @RequestMapping(value = {"/apo/view"}, method = RequestMethod.GET)
    public String viewCourseOffering(Model model, @RequestParam("v") Long offeringId)
    {
        /* Get All Offerings */
        model.addAttribute("allOfferings", offeringService.retrieveAllOfferings());
        model.addAttribute("allDays", offeringService.generateLetterDays());

        /* Find Specific Offering */
        CourseOffering selectedOffering = offeringService.retrieveCourseOffering(offeringId);
        if(selectedOffering == null)
            return "redirect:/error";
        model.addAttribute("selOffering", selectedOffering);

        return "/apo/apoModify";
    }

    @RequestMapping(value = {"/apo", "/apo/home", "/cvc", "/cvc/home"}, method = RequestMethod.POST)
    public String editCourseOffering(@ModelAttribute("offerModifyForm") OfferingModifyDto offerModifyForm,
                                     BindingResult bindingResult, HttpServletRequest request, Model model)
    {
        /* Errors */
        if (bindingResult.hasErrors())
            return "/apo/add-offering";

        /* Else, save new course offering to the database */


        /* Message that course is successfully updated */
        String urlPattern = (String) request.getServletPath();
        if (urlPattern.contains("apo"))
            return "redirect:/apo";
        else if (urlPattern.contains("cvc"))
            return "redirect:/cvc";

        return "redirect:/error";
    }

    /*** Delete Course Offering ***/
    @RequestMapping(value = "/apo/delete-offering", method = RequestMethod.POST)
    public String deleteCourseOfferingSubmit(@RequestParam("offeringID") Long offeringID, Model model)
    {
        /* Find Chosen Offering */
        CourseOffering currOffering = offeringService.retrieveCourseOffering(offeringID);

        if(currOffering == null)
            return "redirect:/error";

        /* Delete from database */
        offeringService.deleteCourseOffering(currOffering);

        /* Message that course offering is successfully updated */
        return "redirect:/apo";
    }

    /*** Room Allocation Feature ***/
    @RequestMapping(value = "/apo/allocate-room", method = RequestMethod.GET)
    public String allocateRoomPage(Model model)
    {
        model.addAttribute("roomList", offeringService.retrieveAllRooms());

        return "/apo/allocate-room";
    }
}
