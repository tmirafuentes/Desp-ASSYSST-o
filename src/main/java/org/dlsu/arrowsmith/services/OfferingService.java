package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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
    private ModifyingCoursesRepository modifyingCoursesRepository;

    public ArrayList<CourseOffering> getSearchCourses() {
        return searchCourses;
    }

    public void setSearchCourses(ArrayList<CourseOffering> searchCourses) {
        this.searchCourses = searchCourses;
    }

    private ArrayList<CourseOffering> searchCourses = new ArrayList<>();
    public ArrayList<CourseOffering> getDayFilteredCourses() {
        return dayFilteredCourses;
    }

    public void setDayFilteredCourses(ArrayList<CourseOffering> dayFilteredCourses) {
        this.dayFilteredCourses = dayFilteredCourses;
    }

    private ArrayList<CourseOffering> dayFilteredCourses = new ArrayList<>();

    public ArrayList<CourseOffering> getFilteredCourses() {
        return filteredCourses;
    }

    public void setFilteredCourses(ArrayList<CourseOffering> filteredCourses) {
        this.filteredCourses = filteredCourses;
    }

    private ArrayList<CourseOffering> filteredCourses = new ArrayList<>();
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

    /* Retrieve All Course Offerings Per Term */
    public ArrayList<CourseOffering> retrieveAllOfferingsByTermErrorChecking(int startAY, int endAY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        return courseOfferings;
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

    /* Retrieve All Course Offerings Per Department Per Term */
    public Iterator retrieveAllOfferingsByDepartment(Department department, int startAY, int endAY, int term) {
        ArrayList<CourseOffering> courseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByCourseDepartmentAndStartAYAndEndAYAndTerm(department, startAY, endAY, term);
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
    /* Retrieve all Offering Sections by Status */
    public Iterator retrieveAllOfferingSections(int startAY, int endAY, int term) {
        ArrayList<CourseOffering> allOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        Set<String> allSections = new HashSet<String>();
        for(CourseOffering c: allOfferings)
            allSections.add(c.getSection());

        ArrayList<String> allSectionsList = new ArrayList<>(allSections);

        return allSectionsList.iterator();
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
    /* Retrieve All Unique Timeslots */
    public Iterator getUniqueTimeSlots()
    {
        ArrayList<Days> allDays = (ArrayList<Days>) daysRepository.findAllByCourseOfferingStartAYAndCourseOfferingEndAYAndCourseOfferingTerm(2016,2017,1);
        ArrayList<String> timeslotList = new ArrayList<>();
        String timeslotTemplate;
        System.out.println("Get Unique TimeSlots");

        for(int i = 0; i < allDays.size(); i++)
        {
            if(!allDays.get(i).getbeginTime().equals("0"))
            {
                timeslotTemplate = allDays.get(i).getbeginTime() + " - " + allDays.get(i).getendTime();
                System.out.println("Hello = " + timeslotTemplate);
                timeslotList.add(timeslotTemplate);
            }

        }
        Set<String> uniqueTimeslots = new HashSet<String>(timeslotList);
        timeslotList = new ArrayList<String>(uniqueTimeslots);
        timeslotList.removeIf( time -> time.replaceAll("\\s+","").equals("-"));
        for(String i: timeslotList)
            System.out.println(i);
        Collections.sort(
                timeslotList,
                (time1, time2) -> Integer.parseInt( time1.substring(0, time1.indexOf(' ')).replaceAll(":","")) - Integer.parseInt(time2.substring(0, time2.indexOf(' ')).replaceAll(":","")));
        //Collections.sort(timeslotList);
        return timeslotList.iterator();
    }
    /* Retrieve All Unique Terms */
    public Iterator getUniqueTerms()
    {
        ArrayList<CourseOffering> allCourses = (ArrayList<CourseOffering>) courseOfferingRepository.findAll();
        ArrayList<Integer> termList = new ArrayList<>();
        int termTemplate;
        for(int i = 0; i < allCourses.size(); i++)
            if(allCourses.get(i).getTerm() > 0)
                termList.add(allCourses.get(i).getTerm());

        Set<Integer> uniqueTerms = new HashSet<Integer>(termList);
        termList = new ArrayList<Integer>(uniqueTerms);
        Collections.sort(termList);
        return termList.iterator();
    }
    /* Retrieve All Unique Statuses
    public Iterator getUniqueStatus()
    {
        ArrayList<CourseOffering> allCourseOfferings = (ArrayList<CourseOffering>) CourseOfferingRepository.get();
        ArrayList<String> timeslotList = new ArrayList<>();
        String timeslotTemplate;


        for(int i = 0; i < allDays.size(); i++)
        {
            timeslotTemplate = allDays.get(i).getbeginTime() + " - " + allDays.get(i).getendTime();
            timeslotList.add(timeslotTemplate);
        }

        Collections.sort(timeslotList); //needs sorting based on time frame
        Set<String> uniqueTimeslots = new HashSet<String>(timeslotList);
        timeslotList = new ArrayList<String>(uniqueTimeslots);
        Collections.sort(timeslotList);

        return timeslotList.iterator();
    }
    */
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

    /* Retrieve all course offerings that are held in a specific room */
    /*
    public ArrayList<CourseOffering> retrieveCourseOfferingbyRoom(String roomCode, int startAY, int endAY, int term) {
        List<CourseOffering> courseOfferingsByRoom = new ArrayList<>();
        Iterator courseIterator = retrieveAllOfferingsByTerm(startAY, endAY, term);


    }*/

    /* Room Rule Checking */
    public ArrayList<Room> roomRuleChecking(char day1, char day2, String startTime, String endTime)
    {
        if(!startTime.equals("") && !endTime.equals(""))
        {
            Iterator<CourseOffering> allCourses = this.retrieveAllOfferingsByTerm(2016, 2017, 1);
            ArrayList<CourseOffering> evaluatedCourses = new ArrayList<>();//List of courses that are "Safe"
            HashSet<String> listofCourseRooms = new HashSet<>();
            ArrayList<Room> finalRoomList = new ArrayList<>();

            CourseOffering currentCourse;
            /* Checks course offerings that are free at this time slot*/
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
            /* For each course offering that do not conflict with the sched, get the room code (also deletes duplicates) */
            for(CourseOffering s: evaluatedCourses)
                for (Days x : s.getDaysSet())
                    listofCourseRooms.add(x.getRoom().getRoomCode());

            /* Search for list of rooms and add it in the final Room List */
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
    /* To be used in conjunction with the Room Checking function */
    public boolean conflictsWith(int firstStart, int firstEnd, int secondStart, int secondEnd) {
        if (firstEnd <= secondStart) {//no conflict
            return false;
        }

        if (secondEnd <= firstStart) {//no conflict
            return false;
        }

        return true;
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
    /*
    public ArrayList<String> generateTerms() {
        ArrayList<String> allTerms = new ArrayList<String>();
        allTerms.add("1st");
        allTerms.add("2nd");
        allTerms.add("3rd");
        return allTerms;
    }
    */
    /* Generate All Room Types */
    public Iterator generateRoomType() {
        ArrayList<String> allRoomTypes = new ArrayList<String>();
        allRoomTypes.add("Lecture");
        allRoomTypes.add("Computer Laboratory");
        return allRoomTypes.iterator();
    }
    /* Sorting the course modification*/
    public Iterator generateSortedCourseOfferings(int startAY, int endAY, int term)
    {
        ArrayList<CourseOffering> allCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        Collections.sort(allCourseOfferings, (p1, p2) -> p1.getCourse().getCourseCode().compareTo(p2.getCourse().getCourseCode()));//sorted by course code

        //sort the days
        return allCourseOfferings.iterator();
    }
    public ArrayList<CourseOffering> generateSortedArrayListCourseOfferings(int startAY, int endAY, int term)
    {
        ArrayList<CourseOffering> allCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        Collections.sort(allCourseOfferings, (p1, p2) -> p1.getCourse().getCourseCode().compareTo(p2.getCourse().getCourseCode()));//sorted by course code

        //sort the days
        return allCourseOfferings;
    }
    /* Filter Functions */
    public ArrayList<CourseOffering> retrieveCourseOfferingSearch(String courseCode)
    {
        ArrayList<CourseOffering> filteredCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByCourseCourseCode(courseCode);
        this.setSearchCourses(filteredCourseOfferings);
        return filteredCourseOfferings;
    }
    public ArrayList<CourseOffering> retrieveCourseOfferingsTerm(int term)
    {
        //Get the filtered list
        ArrayList<CourseOffering> filteredCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, term);

        //ArrayList<CourseOffering> allCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, 1);

        //Get Intersection (filtering) with the current displayed
        //return generateIntersectionLists(filteredCourseOfferings, allCourseOfferings);
        return filteredCourseOfferings;
    }
    public ArrayList<CourseOffering> retrieveCourseOfferingsClassType(String type)
    {
        //Get the filtered list
        ArrayList<CourseOffering> filteredCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStatusAndStartAYAndEndAYAndTerm(type, 2016, 2017, 1);

        //ArrayList<CourseOffering> allCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, 1);

        //Get Intersection (filtering) with the current displayed
        //return generateIntersectionLists(filteredCourseOfferings, allCourseOfferings);


        return filteredCourseOfferings;
    }
    public ArrayList<CourseOffering> retrieveCourseOfferingsRoomType(String type)
    {
        //Get the filtered list
        //ArrayList<CourseOffering> filteredCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStatusAndStartAYAndEndAYAndTerm(type, 2016, 2017, 1);
        //The processed list is also being used in the other function (esp because you're using a list in another list) -> better to use iterator
        Iterator<CourseOffering> allCourseOfferings =  courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, 1).iterator();
        ArrayList<CourseOffering> filteredCourseOfferings = new ArrayList<>();
        while(allCourseOfferings.hasNext())
            {
                CourseOffering cs = allCourseOfferings.next();
                for(Days s: cs.getDaysSet())
                {
                    if(type.equals(s.getRoom().getRoomType()))
                        filteredCourseOfferings.add(cs);//remove all those room type that are not equal
                }
            }

        return filteredCourseOfferings;
    }
    public ArrayList<CourseOffering> retrieveCourseOfferingsTimeslot(String timeslot)
    {
        //Get the filtered list
        //ArrayList<CourseOffering> filteredCourseOfferings = (ArrayList<CourseOffering>) courseOfferingRepository.findAllByStatusAndStartAYAndEndAYAndTerm(type, 2016, 2017, 1);

        Iterator<CourseOffering> allCourseOfferings =  courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, 1).iterator();
        ArrayList<CourseOffering> filteredCourseOfferings = new ArrayList<>();

        while(allCourseOfferings.hasNext())
        {
            CourseOffering cs = allCourseOfferings.next();
            for(Days s: cs.getDaysSet())
            {
                String formedTimeslot = s.getbeginTime() + " - " + s.getendTime();
                if(timeslot.equals(formedTimeslot))
                    filteredCourseOfferings.add(cs);//remove all those timeslots that are not equal
            }
        }

        return filteredCourseOfferings;
    }

    public ArrayList generateIntersectionLists(ArrayList<CourseOffering> firstList, ArrayList<CourseOffering> secondList)
    {
        ArrayList<CourseOffering> newList = new ArrayList<>();

        for(CourseOffering first: firstList)
        {
            if(secondList.contains(first))
                newList.add(first);
        }
        return newList;
    }
    public ArrayList<CourseOffering> getAllCoursesOnDay(char day)
    {
        Iterator<CourseOffering> allCourses = courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, 1).iterator();
        ArrayList<CourseOffering> mondayCourses = new ArrayList<>();
        while(allCourses.hasNext()){
            CourseOffering cs = allCourses.next();
            for(Days s: cs.getDaysSet())
                if(s.getclassDay() == day)
                    mondayCourses.add(cs);
        }
        this.setDayFilteredCourses(mondayCourses);
        return mondayCourses;
    }
    public College findCollegebyCode(String code)
    {
        return collegeRepository.findCollegeByCollegeCode(code);
    }

    /* Course Offering Loads Functions*/
    public ArrayList<CourseOffering> findAllCourseOfferingLoads(User faculty)
    {
        return courseOfferingRepository.findAllByFacultyAndStartAYAndEndAYAndTerm(faculty, 2016, 2017, 1);
    }
    public void saveOnlineUser(OnlineUsers ol)
    {
        onlineUsersRepository.save(ol);
    }

    //returns true if unlocked and false if locked
    public boolean checkLockOffering(Long offeringId)
    {
        ModifyingCourses mc = modifyingCoursesRepository.findByOfferingId(offeringId);
        if(mc != null)//the course is found, the course is locked
            return true;
        else//the course isn't found, the course isn't locked
            return false;
    }

    public boolean checkLockOfferingPerson(Long userId)
    {
        ModifyingCourses mc = modifyingCoursesRepository.findByUserId(userId);
        if(mc != null)//the course is found, the course is locked
            return true;
        else//the course isn't found, the course isn't locked
            return false;
    }

    //Add the course offering to the lock list
    public void lockOffering(Long userId, Long offeringId)
    {
        ModifyingCourses mc = new ModifyingCourses();
        mc.setOfferingId(offeringId);
        mc.setUserId(userId);
        modifyingCoursesRepository.save(mc);
    }

    public void freeOffering(Long userId)
    {
        //Get the size of the list to see if null or not
        ArrayList<ModifyingCourses> mcList = (ArrayList<ModifyingCourses>)modifyingCoursesRepository.findAll();
        int size = mcList.size();
        //will only free if there are courses in the locked list and
        if(size > 0 && !checkLockOffering(modifyingCoursesRepository.findByUserId(userId).getOfferingId()))
        {
            ModifyingCourses mc = modifyingCoursesRepository.findByUserId(userId);
            mc.setOfferingId(null);
            modifyingCoursesRepository.save(mc);
        }
    }

    public ArrayList<OnlineUsers> retrieveAllOnlineUsers()
    {
        return (ArrayList<OnlineUsers>) onlineUsersRepository.findAll();
    }
    public Long findUserOfferingWhereabouts(Long userId)
    {
        return modifyingCoursesRepository.findByUserId(userId).getOfferingId();
    }
    public void logoutOnlineUser(Long ol)
    {
        OnlineUsers onlineUsers = onlineUsersRepository.findByUserId(ol);
        //System.out.println(modifyingCoursesRepository.findByOfferingId(ol).getOfferingId());
        onlineUsersRepository.delete(onlineUsers);
        findAnddeselect(ol);
    }

    public void findAnddeselect(Long userId)
    {
        ArrayList<ModifyingCourses> currentCourses = (ArrayList<ModifyingCourses>) modifyingCoursesRepository.findAll();
        Long holder;
        for(ModifyingCourses a: currentCourses)
        {
            holder = new Long(a.getUserId());
            if(holder.equals(userId))
                modifyingCoursesRepository.delete(a);
        }


    }

}
