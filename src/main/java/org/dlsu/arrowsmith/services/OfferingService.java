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
        Course resultCourse = courseRepository.findCourseByCourse_code(course_code);
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
    public Iterator retrieveAllOfferingsByTerm(int start_AY, int end_AY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStart_AYAndEnd_AYAndTerm(start_AY, end_AY, term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Faculty Per Term */
    public Iterator retrieveAllOfferingsByFaculty(User faculty, int start_AY, int end_AY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByFacultyAndStart_AYAndEnd_AYAndTerm(faculty, start_AY, end_AY, term);
        return courseOfferings.iterator();
    }

    /* Retrieve All Course Offerings Per Course Per Term */
    public Iterator retrieveAllOfferingsByCourse(Course course, int start_AY, int end_AY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByCourseAndStart_AYAndEnd_AYAndTerm(course, start_AY, end_AY, term);
        return courseOfferings.iterator();
    }

    /* Retrieve all Offerings by Faculty */
    public Iterator retrieveAllOfferingsByFaculty(int start_AY, int end_AY, int term, User faculty) {
        ArrayList<CourseOffering> allOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByFacultyAndStart_AYAndEnd_AYAndTerm(faculty, start_AY, end_AY, term);
        return allOfferings.iterator();
    }

    /* Retrieve all Offerings by Status */
    public Iterator retrieveAllOfferingsByStatus(int start_AY, int end_AY, int term, String status) {
        ArrayList<CourseOffering> allOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStatusAndStart_AYAndEnd_AYAndTerm(status, start_AY, end_AY, term);
        return allOfferings.iterator();
    }

    /* Retrieve Specific Offering By ID */
    public CourseOffering retrieveCourseOffering(Long offering_id) {
        return courseOfferingRepository.findCourseOfferingByOffering_id(offering_id);
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
    public Iterator retrieveAllDaysByTimeslot(String begin_time, String end_time) {
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByBegin_timeAndEnd_time(begin_time, end_time);
        return allDays.iterator();
    }

    /* Retrieve All Days Per Room and Time Slot */
    public Iterator retrieveAllDaysByRoomAndTimeslot(Room room, String begin_time, String end_time) {
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByRoomAndBegin_timeAndEnd_time(room, begin_time, end_time);
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

    /* Retrieve All Rooms By Building */
    public Iterator retrieveAllRoomsByBuilding(Building building) {
        ArrayList<Room> allRooms = (ArrayList<Room>) roomRepository.findAllByBuilding(building);
        return allRooms.iterator();
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */

    /* Room Allocation */
    public void assignRoomToAllDaysPerOffering(CourseOffering offering, Room room, char[] class_day, String begin_time, String end_time) {
        Iterator allDays = retrieveAllDaysByOffering(offering);
        int dayCtr = 0;
        while(allDays.hasNext()) {
            Days currDay = (Days) allDays.next();
            currDay.setRoom(room);
            currDay.setBegin_time(begin_time);
            currDay.setEnd_time(end_time);
            currDay.setClass_day(class_day[dayCtr]);

            daysRepository.save(currDay);
        }
    }

    /* Faculty Assignment to Course Offering */
    public void assignFacultyToCourseOffering(CourseOffering offering, User faculty) {
        offering.setFaculty(faculty);
        courseOfferingRepository.save(offering);
    }
}
