package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.*;
import org.dlsu.arrowsmith.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

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
        ArrayList<College> allColleges = (ArrayList<College>) collegeRepository.findAll();
        return allColleges.iterator();
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
    public Course retrieveCourseByCourseCode(String course_code) {
        Course resultCourse = courseRepository.findCourseByCourseCode(course_code);
        return resultCourse;
    }

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
    public Iterator retrieveAllOfferings() {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAll();
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Term */
    public Iterator retrieveAllOfferingsByTerm(int startAY, int endAY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Faculty Per Term */
    public Iterator retrieveAllOfferingsByFaculty(User faculty, int startAY, int endAY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByFacultyAndStartAYAndEndAYAndTerm(faculty, startAY, endAY, term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Course Per Term */
    public Iterator retrieveAllOfferingsByCourse(Course course, int startAY, int endAY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByCourseAndStartAYAndEndAYAndTerm(course, startAY, endAY, term);
        return courseOfferings.iterator();
    }

    /* Retrieve all Offerings by Faculty */
    public Iterator retrieveAllOfferingsByFaculty(User faculty) {
        ArrayList<CourseOffering> allOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByFaculty(faculty);
        return allOfferings.iterator();
    }

    /* Retrieve all Offerings by Status */
    public Iterator retrieveAllOfferingsByStatus(int startAY, int endAY, int term, String status) {
        ArrayList<CourseOffering> allOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStatusAndStartAYAndEndAYAndTerm(status, startAY, endAY, term);
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
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByCourseOffering(offering);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Room */
    public Iterator retrieveAllDaysByRoom(Room room) {
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByRoom(room);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Time Slot */
    public Iterator retrieveAllDaysByTimeslot(String beginTime, String endTime) {
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByBeginTimeAndEndTime(beginTime, endTime);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Room and Time Slot */
    public Iterator retrieveAllDaysByRoomAndTimeslot(Room room, String beginTime, String endTime) {
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByRoomAndBeginTimeAndEndTime(room, beginTime, endTime);
        return allDays.iterator();
    }

    /* Delete Days Per Course Offering */
    public void deleteDaysPerCourseOffering(CourseOffering offering) {
        ArrayList<Days> allDays = (ArrayList<Days>) retrieveAllDaysByOffering(offering);
        for (Days day : allDays)
            daysRepository.delete(day);
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

    /**
     **
     ** BUILDING
     ** CRUD FUNCTIONS
     **
     */

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

    /* Faculty Assignment to Course Offering */
    public void assignFacultyToCourseOffering(CourseOffering offering, User faculty) {
        offering.setFaculty(faculty);
        courseOfferingRepository.save(offering);
    }
    /* Get All degree programs*/
    public Iterator retrieveAllDegreePrograms(){
        ArrayList<DegreeProgram> allDegreePrograms = (ArrayList<DegreeProgram>) degreeProgramRepository.findAll();

        return allDegreePrograms.iterator();
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


    //public Iterator retrieveAllTermsAndAY() {
        /* Get All Offerings */
        //ArrayList<CourseOffering> termsAYear = courseOfferingRepository;
        //return termsAYear.iterator();
    //}
}
