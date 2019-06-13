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
    private CollegeRepository collegeRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private DaysRepository daysRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DegreeProgramRepository degreeProgramRepository;
    @Autowired
    private OnlineUsersRepository onlineUsersRepository;

    @Autowired
    private UserService userService;

    /**
     **
     ** COLLEGE
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update New College */
    public void saveCollege(College college) {
        collegeRepository.save(college);
    }

    /* Retrieve All Colleges */
    public Iterator retrieveAllColleges() {
        return ((ArrayList<College>) collegeRepository.findAll()).iterator();
    }

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
    public Iterator retrieveAllCoursesByDepartment(Department department) {
        ArrayList<Course> allCourses = (ArrayList<Course>) courseRepository.findAllByDepartment(department);
        return allCourses.iterator();
    }

    /* Retrieve Specific Course By Course Code */
    public Course retrieveCourseByCourseCode(String course_code) { return courseRepository.findCourseByCourseCode(course_code); }

    /* Delete Course */
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }

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

    /* Retrieve All Course Offerings */
    public Iterator retrieveAllOfferings() { return courseOfferingRepository.findAll().iterator(); }

    /* Retrieve All Course Offerings Per Term */
    public Iterator retrieveAllOfferingsByTerm(Term term) {
        ArrayList<CourseOffering> courseOfferings = courseOfferingRepository.findAllByTerm(term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Faculty Per Term */
    public Iterator retrieveAllOfferingsByFaculty(User faculty, Term term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByFacultyAndTerm(faculty, term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Course Per Term */
    public Iterator retrieveAllOfferingsByCourse(Course course, Term term) {
        ArrayList<CourseOffering> courseOfferings = courseOfferingRepository.findAllByCourseAndTerm(course, term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Department Per Term */
    public Iterator retrieveAllOfferingsByDepartment(Department department, Term term) {
        ArrayList<CourseOffering> courseOfferings = courseOfferingRepository.findAllByCourseDepartmentAndTerm(department, term);
        return courseOfferings.iterator();
    }

    /* Retrieve all Offerings by Faculty */
    public Iterator retrieveAllOfferingsByFaculty(User faculty) {
        ArrayList<CourseOffering> allOfferings = courseOfferingRepository.findAllByFaculty(faculty);
        return allOfferings.iterator();
    }

    /* Retrieve all Offerings by Status */
    public Iterator retrieveAllOfferingsByStatus(Term term, String status) {
        ArrayList<CourseOffering> allOfferings = courseOfferingRepository.findAllByTypeAndTerm(status, term);
        return allOfferings.iterator();
    }

    /* Retrieve Specific Offering By ID */
    public CourseOffering retrieveCourseOffering(Long offering_id) {
        return courseOfferingRepository.findCourseOfferingByOfferingId(offering_id);
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

    /* Retrieve All Days Per Time Slot */
    public Iterator retrieveAllDaysByTimeslot(String beginTime, String endTime) {
        ArrayList<Days> allDays = daysRepository.findAllByBeginTimeAndEndTime(beginTime, endTime);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Room and Time Slot */
    public Iterator retrieveAllDaysByRoomAndTimeslot(Room room, String beginTime, String endTime) {
        ArrayList<Days> allDays = daysRepository.findAllByRoomAndBeginTimeAndEndTime(room, beginTime, endTime);
        return allDays.iterator();
    }

    /* Delete Days Per Course Offering */
    public void deleteDaysPerCourseOffering(CourseOffering offering) {
        ArrayList<Days> allDays = (ArrayList<Days>) retrieveAllDaysByOffering(offering);
        for (Days day : allDays)
            daysRepository.delete(day);
    }

    /* Delete Specific Day */
    public void deleteSpecificDay(Days dayInstance) {
        daysRepository.delete(dayInstance);
    }

    /**
     **
     ** DEPARTMENT
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Department */
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    /* Retrieve All Departments */
    public Iterator retrieveAllDepartments() {
        ArrayList<Department> allDepartments = (ArrayList<Department>) departmentRepository.findAll();
        return allDepartments.iterator();
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
    public Iterator retrieveAllRooms() {
        ArrayList<Room> allRooms = (ArrayList<Room>) roomRepository.findAll();
        return allRooms.iterator();
    }

    /* Retrieve All Rooms By Building */
    public Iterator retrieveAllRoomsByBuilding(Building building) {
        ArrayList<Room> allRooms = (ArrayList<Room>) roomRepository.findAllByBuilding(building);
        return allRooms.iterator();
    }

    /* Retrieve Room by Room Code */
    public Room retrieveRoomByRoomCode(String roomCode) {
        return roomRepository.findRoomByRoomCode(roomCode);
    }

    /* Room Rule Checking
    public ArrayList<Room> roomRuleChecking(char day1, char day2, String startTime, String endTime)
    {
        if(!startTime.equals("") && !endTime.equals(""))
        {
            Iterator<CourseOffering> allCourses = this.retrieveAllOfferingsByTerm(2016, 2017, 1);
            ArrayList<CourseOffering> evaluatedCourses = new ArrayList<>();//List of courses that are "Safe"
            HashSet<String> listofCourseRooms = new HashSet<>();
            ArrayList<Room> finalRoomList = new ArrayList<>();

            CourseOffering currentCourse;
            /* Checks course offerings that are free at this time slot
            while(allCourses.hasNext())//As long as there are courses in the Iterator: Fill up courses that do not have room conflicts with this timeslot
            {
                currentCourse = allCourses.next();//Get the next element
                for (Days s : currentCourse.getDaysSet()) {//for each day that the course is in
                    if(s.getclassDay() == day1 || s.getclassDay() == day2)//if equal ng day
                    { System.out.println("First Start time and End time: " + Integer.parseInt(startTime) + " " + Integer.parseInt(endTime));
                      System.out.println("Second Start time and End time: " + s.getbeginTime().replace(":", "") + " " + s.getendTime().replace(":", ""));
                        if(!s.getbeginTime().equals(":") && !s.getendTime().equals(":") && !s.getbeginTime().equals("") && !s.getendTime().equals(""))
                        {
                            if(!conflictsWith(Integer.parseInt(startTime), Integer.parseInt(endTime),
                                    Integer.parseInt(s.getbeginTime().replace(":", "")), Integer.parseInt(s.getendTime().replace(":", ""))))
                                evaluatedCourses.add(currentCourse);
                            else
                                continue;
                        }
                    }
                    else
                        evaluatedCourses.add(currentCourse);
                }
            }
            /* For each course offering that do not conflict with the sched, get the room code (also deletes duplicates)
            for(CourseOffering s: evaluatedCourses)
                for (Days x : s.getDaysSet())
                    listofCourseRooms.add(x.getRoom().getRoomCode());

            /* Search for list of rooms and add it in the final Room List
            for(String room: listofCourseRooms)
            {
                if(!room.equals("No Room"))
                    finalRoomList.add(retrieveRoomByRoomCode(room));
            }

            for(Room rum: finalRoomList)
                System.out.println("Room: "+ rum.getRoomCode());

            return finalRoomList;
        }
        else{
           ArrayList<Room> rooms = (ArrayList) roomRepository.findAll();
           return rooms;
        }

    }

    /* To be used in conjunction with the Room Checking function
    public boolean conflictsWith(int firstStart, int firstEnd, int secondStart, int secondEnd) {
        if (firstEnd <= secondStart) {//no conflict
            return false;
        }

        if (secondEnd <= firstStart) {//no conflict
            return false;
        }

        return true;
    }
    */

    /**
     **
     ** BUILDING
     ** CRUD FUNCTIONS
     **
     */

    public Building retrieveBuildingByBuildingCode(String buildingCode)
    {
        return buildingRepository.findBuildingByBldgCode(buildingCode);
    }

    public Iterator retrieveAllBuildings() {
        ArrayList<Building> allBuildings = (ArrayList<Building>) buildingRepository.findAll();
        return allBuildings.iterator();
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */

    /* Room Allocation */
    public void assignRoomToAllDaysPerOffering(CourseOffering offering, Room room, char[] class_day, String beginTime, String endTime) {
        Iterator allDays = retrieveAllDaysByOffering(offering);
        int dayCtr = 0;
        while(allDays.hasNext()) {
            Days currDay = (Days) allDays.next();
            currDay.setRoom(room);
            currDay.setbeginTime(beginTime);
            currDay.setendTime(endTime);
            currDay.setclassDay(class_day[dayCtr]);

            daysRepository.save(currDay);
        }
    }

    /**
     **
     ** OTHER FUNCTIONS
     **
     */

    /* Generate All The Corresponding Letters for Days */
    public ArrayList<String> generateLetterDays() {
        ArrayList<String> allLetterDays = new ArrayList<String>();
        allLetterDays.add("M");
        allLetterDays.add("T");
        allLetterDays.add("W");
        allLetterDays.add("H");
        allLetterDays.add("F");
        allLetterDays.add("S");
        return allLetterDays;
    }

    public ArrayList<String> generateClassType() {
        ArrayList<String> allClassTypes = new ArrayList<String>();
        allClassTypes.add("Regular");
        allClassTypes.add("Elective");
        allClassTypes.add("Special");
        allClassTypes.add("Dissolved");
        return allClassTypes;
    }

    /* Generate All Room Types */
    public Iterator generateRoomType() {
        ArrayList<String> allRoomTypes = new ArrayList<String>();
        allRoomTypes.add("Lecture");
        allRoomTypes.add("Computer Laboratory");
        return allRoomTypes.iterator();
    }

    /*
     *
     *  ASSYSTX 2
     *  OFFERING SERVICES
     *
     */

    /* Retrieve 15 Course Offerings at a time */
    public Page<CourseOffering> retrievePartialCourseOfferings(Term term, PageRequest pageRequest)
    {
        //List<CourseOffering> allOfferings = courseOfferingRepository.findAll(pageRequest.first()).getContent();
        Page<CourseOffering> allOfferings = courseOfferingRepository.findAllByTerm(term, pageRequest);
        return allOfferings;
    }

    /* Retrieve a list of suggested courses */
    public Iterator retrieveSuggestedCourses()
    {
        /* Get All courses */
        Iterator allCourses = retrieveAllCourses();

        /* Create List of Course Codes */
        ArrayList<String> allCourseCodes = new ArrayList<>();
        while(allCourses.hasNext())
        {
            allCourseCodes.add(((Course)allCourses.next()).getCourseCode());
        }

        /* Sort alphabetically */
        Collections.sort(allCourseCodes);

        return allCourseCodes.iterator();
    }

    /* Retrieve Selected Offering through Course Code and Section */
    public CourseOffering retrieveOfferingByCourseCodeAndSection(String course_code, String section)
    {
        /* Retrieve current term */
        Term currTerm = userService.retrieveCurrentTerm();
        return courseOfferingRepository.findCourseOfferingByCourse_CourseCodeAndSectionAndTerm(course_code, section, currTerm);
    }
}
