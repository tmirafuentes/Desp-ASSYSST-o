package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.*;
import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.dlsu.arrowsmith.services.FacultyService;
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
import java.util.Set;

@Controller
public class OfferingController {   // This Controller is for the Course Scheduling Module
    /*** Services ***/
    @Autowired
    private OfferingService offeringService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyService facultyService;

    /*** Extra Stuff ***/
    private MessageSource messages;

    /***
     *
     *  ACTIVE
     *  URL MAPPING
     *
     */

    /*** Add Course Offering ***/
    @RequestMapping(value = "/apo/add-offering", method = RequestMethod.POST)
    public String addNewOfferingSubmit(@ModelAttribute("addOfferingForm") Course offeringForm,
                                       BindingResult bindingResult,
                                       Model model)
    {
        /* Errors */
        if (bindingResult.hasErrors())
            return "/apo/home";

        /* Find Course Object through Course Code */
        Course currCourse = offeringService.retrieveCourseByCourseCode(offeringForm.getCourseCode());

        /* Initialize new Course Offering */
        CourseOffering newOffering = new CourseOffering();
        newOffering.setCourse(currCourse);
        newOffering.setStartAY(2016);
        newOffering.setEndAY(2017);
        newOffering.setTerm(1);

        /* Else, save new course offering to the database */
        offeringService.saveCourseOffering(newOffering);

        /* Message that course is successfully updated */
        return "redirect:/apo/home";
    }

    /* Modify Course Offering */
    @RequestMapping(value = {"/apo", "/apo/home", "/cvc", "/cvc/home"}, method = RequestMethod.POST)
    public String editCourseOffering(@ModelAttribute("offerModifyForm") OfferingModifyDto offerModifyForm,
                                     BindingResult bindingResult, HttpServletRequest request, Model model)
    {
        /* Errors */
        String urlPattern = (String) request.getServletPath();
        if (bindingResult.hasErrors())
            return "/apo";

        System.out.println("Hello Niggers = " + offerModifyForm.toString());

        /* Else, save new course offering to the database */
        CourseOffering currOffering = offeringService.retrieveCourseOffering(offerModifyForm.getOfferingId()); // Offering Id
        currOffering.setSection(offerModifyForm.getClassSection()); // Offering Section
        currOffering.setStatus(offerModifyForm.getClassStatus()); // Offering Status

        // Find Room Object
        Room newRoom = offeringService.retrieveRoomByRoomCode(offerModifyForm.getRoomCode());

        // Days
        Set<Days> daysSet = currOffering.getDaysSet();
        // If there are no current daysSet
        if (daysSet == null)
        {
            // If Day 1 is not null or "-" in the form
            if(!(offerModifyForm.getDay1() == '-'))
            {
                Days newDay1 = new Days();
                newDay1.setclassDay(offerModifyForm.getDay1());
                newDay1.setbeginTime(offerModifyForm.getStartTimeParsed());
                newDay1.setendTime(offerModifyForm.getEndTimeParsed());
                newDay1.setCourseOffering(currOffering);
                newDay1.setRoom(newRoom);
                daysSet.add(newDay1);
            }

            // If Day 2 is not null or "-" in the form
            if(!(offerModifyForm.getDay2() == '-'))
            {
                Days newDay2 = new Days();
                newDay2.setclassDay(offerModifyForm.getDay2());
                newDay2.setbeginTime(offerModifyForm.getStartTimeParsed());
                newDay2.setendTime(offerModifyForm.getEndTimeParsed());
                newDay2.setCourseOffering(currOffering);
                newDay2.setRoom(newRoom);
                daysSet.add(newDay2);
            }
        }
        // If there is a DaysSet
        else {
            boolean isDay1Done = false;
            for(Days dayInstance : daysSet)
            {
                // If Day 1 is not null or not "-" in the form
                if(!(offerModifyForm.getDay1() == '-') && !isDay1Done)
                {
                    dayInstance.setclassDay(offerModifyForm.getDay1());
                    dayInstance.setbeginTime(offerModifyForm.getStartTimeParsed());
                    dayInstance.setendTime(offerModifyForm.getEndTimeParsed());
                    dayInstance.setCourseOffering(currOffering);
                    dayInstance.setRoom(newRoom);
                    isDay1Done = true;
                    continue;
                }
                // If Day 1 is null or "-" in the form
                else if(offerModifyForm.getDay1() == '-' && !isDay1Done)
                {
                    daysSet.remove(dayInstance);
                    isDay1Done = true;
                    continue;
                }

                // If Day 2 is not null or not "-" in the form
                if(!(offerModifyForm.getDay2() == '-') && isDay1Done)
                {
                    dayInstance.setclassDay(offerModifyForm.getDay2());
                    dayInstance.setbeginTime(offerModifyForm.getStartTimeParsed());
                    dayInstance.setendTime(offerModifyForm.getEndTimeParsed());
                    dayInstance.setCourseOffering(currOffering);
                    dayInstance.setRoom(newRoom);
                }
                // If Day 2 is null or "-" in the form
                else if(offerModifyForm.getDay2() == '-' && isDay1Done)
                {
                    daysSet.remove(dayInstance);
                }
            }
        }

        // Faculty
        User currFaculty = currOffering.getFaculty();   // Get assigned faculty
        User newFaculty = userService.findUserByFirstNameLastName(offerModifyForm.getFaculty());    // Get newly assigned faculty

        // If currFaculty is not null and curr and new are not the same
        if (currFaculty.getUserId() == 1111111 && currFaculty.getUserId() != newFaculty.getUserId())
        {
            // Retrieve Faculty Load of current faculty
            FacultyLoad currFacultyLoad = facultyService.retrieveFacultyLoadByFaculty(currOffering.getStartAY(), currOffering.getEndAY(),
                    currOffering.getTerm(), currFaculty);

            // Subtract Units to Faculty Load
            currFacultyLoad.setTeachingLoad(currFacultyLoad.getTeachingLoad() - currOffering.getCourse().getUnits());

            // Save current faculty load to the database
            facultyService.saveFacultyLoad(currFacultyLoad);

            currOffering.setFaculty(newFaculty);    // Assign faculty to Course Offering

            // Retrieve Faculty Load of faculty
            FacultyLoad newFacultyLoad = facultyService.retrieveFacultyLoadByFaculty(currOffering.getStartAY(), currOffering.getEndAY(),
                    currOffering.getTerm(), newFaculty);

            // Add Units to Faculty Load
            newFacultyLoad.setTeachingLoad(newFacultyLoad.getTeachingLoad() + currOffering.getCourse().getUnits());

            // Save new faculty load to the database
            facultyService.saveFacultyLoad(newFacultyLoad);
        }
        // Newly assigned currFaculty
        else if (currFaculty.getUserId() == 11111111 && newFaculty != null)
        {
            currOffering.setFaculty(newFaculty);    // Assign faculty to Course Offering

            // Retrieve Faculty Load of faculty
            FacultyLoad newFacultyLoad = facultyService.retrieveFacultyLoadByFaculty(currOffering.getStartAY(), currOffering.getEndAY(),
                    currOffering.getTerm(), newFaculty);

            // Add Units to Faculty Load
            newFacultyLoad.setTeachingLoad(newFacultyLoad.getTeachingLoad() + currOffering.getCourse().getUnits());

            // Save new faculty load to the database
            facultyService.saveFacultyLoad(newFacultyLoad);
        }

        // Save it to the database
        offeringService.saveCourseOffering(currOffering);

        /* Message that course is successfully updated */
        if (urlPattern.contains("apo"))
            return "redirect:/apo";
        else if (urlPattern.contains("cvc"))
            return "redirect:/cvc";

        return "redirect:/error";
    }

    /***
     *
     *  INACTIVE
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

    /* Search Course Offering
    @RequestMapping(value = "/apo", method = RequestMethod.POST)
    public String searchOffering(Model model, @RequestParam("searchString") String searchString)
    {
        System.out.println("Hello World");
        Course searchedCourse = offeringService.retrieveCourseByCourseCode(searchString);
        if(searchedCourse != null)
        //static for now
            model.addAttribute("allOfferings", offeringService.retrieveAllOfferingsByCourse(searchedCourse, 2017, 2018, 1));

        return "/apo";
    } */
    /* Search by Class Type */
    @RequestMapping(value = "/apo/class-type", method = RequestMethod.GET)
    public String searchClassType(Model model, @RequestParam("select_left_class_type") String classType)
    {
        model.addAttribute("allOfferings", offeringService.retrieveAllOfferingsByStatus(2017, 2018, 1, classType));
        return "/apo/search-type";
    }
}
