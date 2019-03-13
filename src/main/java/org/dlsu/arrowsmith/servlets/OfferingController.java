package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.dtos.OfferingModifyDto;
import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.services.OfferingService;
import org.dlsu.arrowsmith.services.UserService;
import org.dlsu.arrowsmith.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
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
        model.addAttribute("allOfferings", offeringService.generateSortedCourseOfferings(2016, 2017, 1));
        return "redirect:/apo/home";
    }
    ///apo", "/apo/home", "/cvc", "/cvc/home
    //value = {"/apo/modifyOffering"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    /* Modify Course Offering */

    //@RequestMapping(value = {"/apo/modifyOffering", "/cvc", "/cvc/home"}, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    /*public String editCourseOffering(@ModelAttribute("offerModifyForm") OfferingModifyDto offerModifyForm,
                                                   Model model, BindingResult bindingResult)
    {
        /* Errors
        //String urlPattern = (String) request.getServletPath();
        if (bindingResult.hasErrors())
            return "Sorry, an error has occured. Course Offering has not been modified.";
           */
        /* Else, save new course offering to the database
        CourseOffering currOffering = offeringService.retrieveCourseOffering(offerModifyForm.getOfferingId()); // Offering Id
       // if(ifSectionExists(offerModifyForm.getClassSection(),  2016, 2017, 1))
            currOffering.setSection(offerModifyForm.getClassSection()); // Offering Section
       // else
           // currOffering.setSection("INVALID");

        currOffering.setStatus(offerModifyForm.getClassStatus()); // Offering Status

        // Find Room Object
        Room newRoom = offeringService.retrieveRoomByRoomCode(offerModifyForm.getRoomCode());

        // Days
        Set<Days> daysSet = currOffering.getDaysSet();

        boolean noConflicts = true;

        // If there are no current daysSet (ohhh rewriting on it)
        if (daysSet == null)
        {
            // If Day 1 is not null or "-" in the form
            if(!(offerModifyForm.getDay1() == '-') && noConflicts)
            {
                Days newDay1 = new Days();
                newDay1.setclassDay(offerModifyForm.getDay1());
                newDay1.setbeginTime(offerModifyForm.getStartTimeParsed());
                newDay1.setendTime(offerModifyForm.getEndTimeParsed());
                newDay1.setCourseOffering(currOffering);
                newDay1.setRoom(newRoom);
                //TO DO: WHAT ABOUT BLANKS IN THE TIME
                if(!classScheduleConflictsWith(currOffering.getofferingId(), newDay1.getRoom(), newDay1.getclassDay(),
                                                Integer.parseInt(newDay1.getbeginTime()), Integer.parseInt(newDay1.getendTime())
                        ,2016, 2017,  1))
                {
                    daysSet.add(newDay1);
                    //System.out.println("Conflict not found!");
                }

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
                if(!classScheduleConflictsWith(currOffering.getofferingId(),newDay2.getRoom(), newDay2.getclassDay(), Integer.parseInt(newDay2.getbeginTime()), Integer.parseInt(newDay2.getendTime())
                        ,2016, 2017, 1))
                {
                    daysSet.add(newDay2);
                    //System.out.println("Conflict not found!");
                }
            }
        }
        // If there is a DaysSet
        else {
            boolean isDay1Done = false;
            boolean conflictFound = false;
            for(Days dayInstance : daysSet)
            {
                if(!conflictFound)
                {
                    // If Day 1 is not null or not "-" in the form
                    if(!(offerModifyForm.getDay1() == '-') && !isDay1Done)//days is done is used to
                    {
                        if(!classScheduleConflictsWith(currOffering.getofferingId(),newRoom, offerModifyForm.getDay1(), Integer.parseInt(offerModifyForm.getStartTimeParsed()), Integer.parseInt(offerModifyForm.getEndTimeParsed())
                                ,2016, 2017,  1))
                        {
                            dayInstance.setclassDay(offerModifyForm.getDay1());
                            //dayInstance.setbeginTime(offerModifyForm.getStartTimeParsed());
                            //dayInstance.setendTime(offerModifyForm.getEndTimeParsed());
                            dayInstance.setCourseOffering(currOffering);
                            dayInstance.setRoom(newRoom);
                            isDay1Done = true;
                            continue;
                        }
                        else
                        {
                            conflictFound = true;
                            System.out.println("Conflict Found");
                        }
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
                        if(!classScheduleConflictsWith(currOffering.getofferingId(),newRoom, offerModifyForm.getDay2(), Integer.parseInt(offerModifyForm.getStartTimeParsed()), Integer.parseInt(offerModifyForm.getEndTimeParsed())
                                ,2016, 2017, 1)) {
                            dayInstance.setclassDay(offerModifyForm.getDay2());
                            //dayInstance.setbeginTime(offerModifyForm.getStartTimeParsed());
                            //dayInstance.setendTime(offerModifyForm.getEndTimeParsed());
                            dayInstance.setCourseOffering(currOffering);
                            dayInstance.setRoom(newRoom);
                        }
                        else
                        {
                            conflictFound = true;
                            System.out.println("Conflict Found");
                        }
                    }
                    // If Day 2 is null or "-" in the form
                    else if(offerModifyForm.getDay2() == '-' && isDay1Done)
                    {
                        daysSet.remove(dayInstance);
                    }
                }
            }
        }

        // Faculty
        User currFaculty = currOffering.getFaculty();   // Get assigned faculty
        User newFaculty = userService.findUserByFirstNameLastName(offerModifyForm.getFaculty());    // Get newly assigned faculty

        // If currFaculty is not null and curr and new are not the same
        if (currFaculty.getUserId() == 1111111 && currFaculty.getUserId() != newFaculty.getUserId() && !facultyService.checkFacultyloadingCourseOfferingsConflicts(newFaculty, 2016, 2017, 1, currOffering))
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
        else if (currFaculty.getUserId() == 11111111 && newFaculty != null && !facultyService.checkFacultyloadingCourseOfferingsConflicts(newFaculty, 016, 2017, 1, currOffering))
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

        /* Message that course is successfully updated
        /*
        if (urlPattern.contains("apo"))
            return "redirect:/apo";
        else if (urlPattern.contains("cvc"))
            return "redirect:/cvc";

        return "redirect:/error";

        model.addAttribute("allOfferings", offeringService.retrieveAllOfferingsByTerm(2016, 2017, 1));
        //System.out.println("umaabot sa last");
        return "";
    }*/

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

    /**
     **
     ** RULE CHECKING FUNCTIONS
     **
     */
    /*
    public boolean checkRoomChange(int startAY, int endAY, int term, Set<Days> daysSet, Room newRoom, )
    {
        boolean isTaken = false;
        //get arraylist of current offerings
        ArrayList<CourseOffering> termCourseOfferings = offeringService.retrieveAllOfferingsByTermErrorChecking(startAY, endAY, term);
        //loop through offerings and check if both the time, the room, and the days are matched
        for (int i = 0; i < termCourseOfferings.size(); i++)
        {
            //days

            //time
            //room
        }


        return isTaken;
    }
    */
    public boolean checkRoom(Room originalValue, Room replacementValue)
    {
        return originalValue.getRoomCode() == replacementValue.getRoomCode();
    }
    public boolean checkDaysSet(Set<Days> originalValues, Set<Days> replacementValues)
    {
        boolean isTaken = false;
        Iterator<Days> originalIterator = originalValues.iterator();
        Iterator<Days> replacementIterator = replacementValues.iterator();
        while(originalIterator.hasNext())
        {
            while(replacementIterator.hasNext())
            {
               isTaken = checkTwoDays(originalIterator.next(), replacementIterator.next());
            }
        }

        return isTaken;

    }
    public boolean checkTwoDays(Days originalValue, Days replacementValue)
    {
        boolean isTaken = false;

        if(originalValue.getbeginTime() == replacementValue.getbeginTime())
            isTaken = true;
        if(originalValue.getendTime() == replacementValue.getendTime())
            isTaken = true;
        if(originalValue.getclassDay() == replacementValue.getclassDay())
            isTaken = true;
        if(checkRoom(originalValue.getRoom(), replacementValue.getRoom()))
            isTaken = true;

        return isTaken;
    }

    public boolean ifSectionExists(String currentValue, int startAY, int endAY, int term)
    {
        boolean itExists = false;
        Iterator<String> allSections = offeringService.retrieveAllOfferingSections(startAY, endAY, term);
        while (allSections.hasNext())
        {
            String roomIterate = allSections.next();
            System.out.println(roomIterate);
            if(roomIterate.equals(currentValue))
            {
                itExists = true;
            }
        }
        return itExists;
    }

    public boolean conflictsWith(int firstStart, int firstEnd, int secondStart, int secondEnd) {
        if (firstEnd <= secondStart) {//no conflict
            //System.out.println("No conflict1");
            //System.out.println(firstEnd+ " <= " + secondStart + ":" + secondEnd + " <= " + firstStart );
            return false;
        }

        if (secondEnd <= firstStart) {//no conflict
            //System.out.println("No conflict2");
            //System.out.println(firstEnd+ " <= " + secondStart + ":" + secondEnd + " <= " + firstStart );
            return false;
        }

        System.out.println(firstEnd+ " <= " + secondStart + ":" + secondEnd + " <= " + firstStart );
        //System.out.println("Conflicts");
        return true;
    }
    /* Returns false if there are no conflicts with classes using room, class days and timeslots */
    public boolean classScheduleConflictsWith(Long courseID, Room courseRoom, char assignedDay, int startTime, int endTime, int startAY, int endAY, int term)
    {
        boolean isConflict = false;
        Iterator<CourseOffering> allCourses = offeringService.retrieveAllOfferingsByTerm(startAY, endAY, term);
        ArrayList<CourseOffering> evaluatedCourses = new ArrayList<>();//List that will contain courses that run in the same room
        CourseOffering currentCourse;
        //STEP 1: check all who use this room
        while(allCourses.hasNext())//As long as there are courses in the Iterator
        {

            currentCourse = allCourses.next();//Get the next element
            if(currentCourse.getofferingId() != courseID)
            {
                for (Days s : currentCourse.getDaysSet()) {//for each day that the course is in
                    if(s.getRoom().getRoomCode().equals(courseRoom.getRoomCode()))//find courses that use the same room
                    {
                        evaluatedCourses.add(currentCourse);
                        System.out.println("Added: " + currentCourse.getCourse().getCourseCode() + " " + currentCourse.getSection());
                    }
                }
            }

        }
        //STEP 2: Find conflicting days and timeslots per course that uses the same room
        if(evaluatedCourses.size() > 0)//checking if may laman talaga yung list
        {
            for(CourseOffering co: evaluatedCourses)//for each course in the list of courses
            {
                for (Days currentList : co.getDaysSet())//for each day the courseofferings in the list has
                {
                    if(currentList.getclassDay() == assignedDay)//if they have the same class day
                    {
                        System.out.println(currentList.getclassDay() + " vs " + assignedDay);
                        isConflict = !conflictsWith(startTime, endTime
                                ,Integer.parseInt(currentList.getbeginTime()),
                                    Integer.parseInt(currentList.getendTime()));//check if they have conflict with timeslots
                    }
                }
            }
        }
        return isConflict;
    }
}
