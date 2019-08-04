package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OfferingService {
    /* Repositories */
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private DaysRepository daysRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserService userService;

    /**
     **
     ** COURSE
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update New Course */
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    /* Retrieve All Courses */
    public Iterator retrieveAllCourses() {
        ArrayList<Course> allCourses = (ArrayList<Course>) courseRepository.findAll();
        return allCourses.iterator();
    }

    /* Retrieve All Courses By College */
    public Iterator retrieveAllCoursesByCollege(College college) {
        ArrayList<Course> allCourses = (ArrayList<Course>) courseRepository.findAllByCollege(college);
        return allCourses.iterator();
    }

    /* Retrieve All Courses By Department */
    public Iterator retrieveAllCoursesByDepartment(Department department) { return courseRepository.findAllByDepartment(department).iterator(); }

    /* Retrieve Specific Course By Course Code */
    public Course retrieveCourseByCourseCode(String course_code) { return courseRepository.findCourseByCourseCode(course_code); }

    /* Retrieve a list of suggested courses */
    public Iterator retrieveSuggestedCourses()
    {
        /* Get All courses */
        Iterator allCourses = retrieveAllCourses();

        /* Create List of Course Codes */
        ArrayList<String> allCourseCodes = new ArrayList<>();
        while(allCourses.hasNext())
            allCourseCodes.add(((Course)allCourses.next()).getCourseCode());

        /* Sort alphabetically */
        Collections.sort(allCourseCodes);

        return allCourseCodes.iterator();
    }

    /* Delete Course */
    public void deleteCourse(Course course) { courseRepository.delete(course); }

    /**
     **
     ** COURSE OFFERING
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Course Offering */
    public void saveCourseOffering(CourseOffering courseOffering) {
        courseOfferingRepository.save(courseOffering);
    }

    /* Retrieve Specific Offering By ID */
    public CourseOffering retrieveCourseOffering(Long offering_id) { return courseOfferingRepository.findCourseOfferingByOfferingId(offering_id); }

    /* Retrieve Selected Offering through Course Code and Section */
    public CourseOffering retrieveOfferingByCourseCodeAndSection(String course_code, String section)
    {
        /* Retrieve current term */
        Term currTerm = userService.retrieveCurrentTerm();
        return courseOfferingRepository.findCourseOfferingByCourse_CourseCodeAndSectionAndTerm(course_code, section, currTerm);
    }

    /* Retrieve All Course Offerings Per Term */
    public Iterator retrieveAllOfferingsByTerm(Term term)
    {
        /* Determine User */
        User currUser = userService.retrieveUser();
        ArrayList<CourseOffering> finalListOfferings = null;

        if (currUser.getUserType().equals("Academic Programming Officer") ||
            currUser.getUserType().equals("Dean") ||
            currUser.getUserType().equals("Vice-Dean"))
            finalListOfferings = courseOfferingRepository.findAllByTerm(term);
        else if (currUser.getUserType().equals("Chair") ||
                 currUser.getUserType().equals("Vice-Chair"))
        {
            finalListOfferings = courseOfferingRepository.findAllByCourseDepartmentAndTerm(currUser.getDepartment(), term);
            ArrayList<CourseOffering> serviceCourses = courseOfferingRepository.findAllByServiceToAndTerm(currUser.getDepartment().getDeptId(), term);
            finalListOfferings.addAll(serviceCourses);
        }

        return finalListOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Faculty Per Term */
    public Iterator retrieveAllOfferingsByFaculty(User faculty, Term term) {
        ArrayList<CourseOffering> courseOfferings = courseOfferingRepository.findAllByFacultyAndTerm(faculty, term);
        return courseOfferings.iterator();
    }

    public boolean isCourseTaughtByFaculty(User faculty, Course course)
    {
        ArrayList<CourseOffering> courseOfferings = courseOfferingRepository.findAllByFacultyAndCourse(faculty, course);

        if(courseOfferings.size() == 0)
            return false;
        return true;
    }

    /* Retrieve All Course Offerings Per Department Per Term */
    public Iterator retrieveAllOfferingsByDepartment(Department department, Term term) {
        ArrayList<CourseOffering> courseOfferings = courseOfferingRepository.findAllByCourseDepartmentAndTerm(department, term);
        return courseOfferings.iterator();
    }

    /* Retrieve 15 Course Offerings at a time */
    public Page<CourseOffering> retrievePartialCourseOfferings(Term term, PageRequest pageRequest)
    {
        //List<CourseOffering> allOfferings = courseOfferingRepository.findAll(pageRequest.first()).getContent();
        Page<CourseOffering> allOfferings = courseOfferingRepository.findAllByTermOrderByOfferingIdAsc(term, pageRequest);
        return allOfferings;
    }

    /* Delete Specific Offering by ID */
    public void deleteCourseOffering(CourseOffering offering) {
        courseOfferingRepository.delete(offering);
    }

    /**
     **
     ** DAYS
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Days */
    public void saveDays(Days days) {
        daysRepository.save(days);
    }

    public Days retrieveSpecificDaysByID(Long id) { return daysRepository.findByDaysId(id); }

    /* Retrieve All Days Per Offering */
    public Iterator retrieveAllDaysByOffering(CourseOffering offering) {
        ArrayList<Days> allDays = daysRepository.findAllByCourseOffering(offering);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Room and Term */
    public Iterator retrieveAllDaysByRoomAndTerm(Room room, Term term) {
        ArrayList<Days> allDays = daysRepository.findAllByRoomAndCourseOffering_Term(room, term);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Building and Time Slot */
    public Iterator retrieveAllDaysByBuildingAndTimeslot(Building building, String beginTime, String endTime, Term term)
    {
        /* Transform into integers */
        int begin_time = Integer.parseInt(beginTime);
        int end_time = Integer.parseInt(endTime);

        /* Retrieve List of all Days in the term */
        ArrayList<Days> allDays = daysRepository.findAllByRoomBuildingAndCourseOfferingTerm(building, term);

        /* Traverse and create a new list */
        ArrayList<Days> filteredList = new ArrayList<>();
        for(Days day : allDays)
        {
            /* Transform days' times into integers */
            int dayBeginTime = Integer.parseInt(day.getbeginTime());
            int dayEndTime = Integer.parseInt(day.getendTime());

            /* Check if dayBeginTime is between begin and end time.
             * If so, include the day in the list.
             */
            if(begin_time >= dayBeginTime && begin_time < dayEndTime)
                filteredList.add(day);
            else if(end_time > dayBeginTime && end_time < dayEndTime)
                filteredList.add(day);
        }

        return filteredList.iterator();
    }

    /* Delete Specific Day */
    public void deleteSpecificDay(Days dayInstance) {
        daysRepository.delete(dayInstance);
    }

    /**
     **
     ** ROOM
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Room */
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    /* Retrieve All Rooms */
    public Iterator retrieveAllRooms()
    {
        ArrayList<Room> allRooms = (ArrayList<Room>) roomRepository.findAll();
        return allRooms.iterator();
    }

    /* Retrieve All Rooms By Building */
    public Iterator retrieveAllRoomsByBuilding(Building building) { return roomRepository.findAllByBuilding(building).iterator(); }

    /* Retrieve All Rooms By Building and Room Type */
    public Iterator retrieveAllRoomsByBuildingAndRoomType(Building building, String roomType)
    {
        return roomRepository.findAllByBuildingAndRoomType(building, roomType).iterator();
    }

    /* Retrieve Room by Room Code */
    public Room retrieveRoomByRoomCode(String roomCode) {
        return roomRepository.findRoomByRoomCode(roomCode);
    }

    /**
     **
     ** BUILDING
     ** CRUD FUNCTIONS
     **
     */

    public Building retrieveBuildingByBuildingCode(String buildingCode) { return buildingRepository.findBuildingByBldgCode(buildingCode); }

    public Iterator retrieveAllBuildings() {
        ArrayList<Building> allBuildings = (ArrayList<Building>) buildingRepository.findAll();
        return allBuildings.iterator();
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */
}
