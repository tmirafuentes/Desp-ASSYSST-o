package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

@Service
public class FacultyService {
    /* Repositories */
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private DeloadingRepository deloadingRepository;
    @Autowired
    private DeloadInstanceRepository deloadInstanceRepository;
    @Autowired
    private FacultyLoadRepository facultyLoadRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     **
     ** DELOADING
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Deloading */
    public void saveDeloading(Deloading deloading) {
        deloadingRepository.save(deloading);
    }

    /* Retrieve All Deloading */
    public Iterator retrieveAllDeloading() {
        ArrayList<Deloading> allDeloadings = (ArrayList<Deloading>) deloadingRepository.findAll();
        return allDeloadings.iterator();
    }

    /* Retrieve Specific Deloading by Deload Code */
    public Deloading retrieveDeloadingByDeloadCode(String deloadCode)
    {
        Deloading deloading = deloadingRepository.findDeloadingByDeloadCode(deloadCode);
        return deloading;
    }

    /**
     **
     ** DELOAD INSTANCE
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Deload Instance */
    public void saveDeloadInstance(DeloadInstance deloadInstance) {
        deloadInstanceRepository.save(deloadInstance);
    }

    public DeloadInstance retrieveDeloadInstanceByID(Long id)
    {
        return deloadInstanceRepository.findByDeloadInId(id);
    }

    /* Retrieve all Deload Instance per Term */
    public Iterator retrieveAllDeloadInstanceByTerm(Term term) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByTerm(term);
        return deloadInstances.iterator();
    }

    /* Retrieve all Deload Instance of a specific Faculty in a given term */
    public Iterator retrieveAllDeloadInstanceByFaculty(Term term, User faculty) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByTermAndFaculty(term, faculty);
        return deloadInstances.iterator();
    }

    /* Retrieve all Deload Instance per Deloading Type in a given time */
    public Iterator retrieveAllDeloadInstanceByType(Term term, Deloading deloading) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByTermAndDeloading(term, deloading);
        return deloadInstances.iterator();
    }

    /**
     **
     ** FACULTY LOAD
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Faculty Load */
    public void saveFacultyLoad(FacultyLoad facultyLoad) {
        facultyLoadRepository.save(facultyLoad);
    }

    public Iterator retrieveAllFacultyLoadByFaculty(User faculty) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByFaculty(faculty);
        return allLoads.iterator();
    }

    public Iterator retrieveAllFacultyLoad() {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAll();
        return allLoads.iterator();
    }

    /* Retrieve All Load Per Term */
    public Iterator retrieveAllFacultyLoadByTerm(Term term) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByTerm(term);
        return allLoads.iterator();
    }

    /* Retrieve All Faculty Load Per Department */
    public Iterator retrieveAllFacultyLoadByTerm(Term term, Department department) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByTermAndDepartment(term, department);
        return allLoads.iterator();
    }

    public ArrayList<FacultyLoad> retrieveAllListFacultyLoadByTerm(Term term, Department department) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByTermAndDepartment(term, department);
        return allLoads;
    }

    /* Retrieve All Faculty Load Per College */
    public Iterator retrieveAllFacultyLoadByTerm(Term term, College college) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByTermAndCollege(term, college);
        return allLoads.iterator();
    }

    /* Retrieve Faculty Load of a Faculty per Term */
    public FacultyLoad retrieveFacultyLoadByFaculty(Term term, User faculty) {
        FacultyLoad facultyLoad = (FacultyLoad) facultyLoadRepository.findFacultyLoadByTermAndFaculty(term, faculty);
        return facultyLoad;
    }

    /* Retrieve Faculty Load by ID*/
    public FacultyLoad retrieveFacultyLoadByID(Long facultyID)
    {
        FacultyLoad facultyLoad = (FacultyLoad) facultyLoadRepository.findFacultyLoadByLoadId(facultyID);


        return facultyLoad;
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */

    /* Summation of Faculty Load in a given Term */
    public void addTotalFacultyLoad(Term term, User faculty) {
        if(checkFacultyInDatabase(term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(term, faculty);
            double totalLoad = 0.0;
            totalLoad += facultyLoad.getAdminLoad() + facultyLoad.getNonacadLoad() +
                    facultyLoad.getResearchLoad() + facultyLoad.getTeachingLoad();
            facultyLoad.setTotalLoad(totalLoad);
            facultyLoadRepository.save(facultyLoad);
        }
    }

    /* Set Faculty to Leave in a given Term */
    public void setLeaveFaculty(Term term, User faculty, String leave_type) {
        if(checkFacultyInDatabase(term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(term, faculty);
            facultyLoad.setOnLeave(true);
            facultyLoad.setLeaveType(leave_type);
        }
    }

    /* Assign Academic Load to a Faculty in a given Term */
    public void assignAcademicLoadToFaculty(Term term, User faculty, double units)
    {
        /* Assign Faculty to Course Offering */
        FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(term, faculty);
        facultyLoad.setTeachingLoad(facultyLoad.getTeachingLoad() + units);
        facultyLoadRepository.save(facultyLoad);
    }

    /* Get unique number of courses taught by Faculty in a given Term */
    public int retrieveFacultyPreparations(Term term, User faculty)
    {
        /* Retrieve offerings assigned to faculty */
        ArrayList<CourseOffering> offerings = courseOfferingRepository.findAllByFacultyAndTerm(faculty, term);

        /* Traverse through each offering */
        ArrayList<String> uniqueOfferings = new ArrayList<>();
        for(CourseOffering o : offerings)
        {
            if(!uniqueOfferings.contains(o.getCourse().getCourseCode()))
                uniqueOfferings.add(o.getCourse().getCourseCode());
        }

        /* Update faculty load */
        FacultyLoad facultyLoad = retrieveFacultyLoadByFaculty(term, faculty);
        facultyLoad.setPreparations(uniqueOfferings.size());
        facultyLoadRepository.save(facultyLoad);

        /* Return value */
        return uniqueOfferings.size();
    }

    /* Assign Deloading Load to a Faculty in a given Term
    public void assignDeloadToFaculty(Term term, User faculty, DeloadInstance deloadingInstance) {
        if(checkFacultyInDatabase(term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(term, faculty);
            if(deloadingInstance.getDeloading().getDeloadType().equals("AL"))
            {
                if(!(deloadingInstance.getDeloading().getUnits() > facultyLoad.getAdminLoad()))
                {
                    facultyLoad.setAdminLoad(facultyLoad.getAdminLoad() - deloadingInstance.getDeloading().getUnits());
                    facultyLoad.setDeloadedLoad(facultyLoad.getDeloadedLoad() + deloadingInstance.getDeloading().getUnits());
                }
            }
            else if(deloadingInstance.getDeloading().getDeloadType().equals("RL")) {
                if(!(deloadingInstance.getDeloading().getUnits() > facultyLoad.getResearchLoad())) {
                    facultyLoad.setResearchLoad(facultyLoad.getResearchLoad() - deloadingInstance.getDeloading().getUnits());
                    facultyLoad.setDeloadedLoad(facultyLoad.getDeloadedLoad() + deloadingInstance.getDeloading().getUnits());
                }
            }
        }
    } */
    //    Retrieve All Deload Instances
    public Iterator<DeloadInstance> retrieveFacultyDeloadings() {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAll();
        Collections.sort(deloadInstances, new Comparator<DeloadInstance>() {
            public int compare(DeloadInstance s1, DeloadInstance s2) {
                return s1.getDeloading().getDeloadType().compareTo(s2.getDeloading().getDeloadType());
            }
        });
        return deloadInstances.iterator();

    }

//      check if faculty loading is applicable

    public boolean checkFacultyLoadDeload(User faculty, Term term)
    {
        FacultyLoad facultyload = retrieveFacultyLoadByFaculty(term, faculty);
        if(facultyload.getTotalLoad() <= 0)
            return false;
        return true;
    }

    public boolean checkFacultyLoading(User faculty, Term term, String loadType)
    {
        FacultyLoad facultyload = retrieveFacultyLoadByFaculty(term, faculty);
        if(facultyload.getTotalLoad() >= 12)
            return false;
        if(loadType.equals("AL") && facultyload.getAdminLoad() >= 6)
            return false;
        return true;
    }
    /**
     **
     ** OTHER FUNCTIONS
     **
     */

    public boolean checkFacultyInDatabase(Term term, User faculty) {
        if (retrieveFacultyLoadByFaculty(term, faculty) != null)
            return true;
        return false;
    }


    /* Generate All The types of faculty loads */
    public ArrayList<String> generateFacultyLoadTypes() {
        ArrayList<String> allFacultyLoadTypes = new ArrayList<String>();
        allFacultyLoadTypes.add("Administrative");
        allFacultyLoadTypes.add("Teaching");
        allFacultyLoadTypes.add("Research");
        return allFacultyLoadTypes;
    }

    public ArrayList<String> retrieveAllFacultyDepartments(){
        ArrayList<String> allFacultyDepartments = new ArrayList<>();
        ArrayList<Department> allDepartments = (ArrayList) departmentRepository.findAll();
        for(Department d: allDepartments)
            allFacultyDepartments.add(d.getDeptCode());
        return allFacultyDepartments;
    }
    public Department retrieveDepartmentByCode(String code)
    {
        return departmentRepository.findDepartmentByDeptCode(code);
    }

    /* TODO: Revise deloading procedures */
    public void undergoDeloading(User faculty, String deloadCode)
    {
        System.out.println("Deload Code: " + deloadCode);
        Deloading deloadingToBeUsed = deloadingRepository.findDeloadingByDeloadCode(deloadCode);
        DeloadInstance deloadInstance = new DeloadInstance();


        deloadInstance.setDeloading(deloadingToBeUsed);
        deloadInstance.setFaculty(faculty);
        //deloadInstance.setTerm(1);
        System.out.println("Deload Units " + deloadInstance.getDeloading().getUnits());
        //this.assignDeloadToFaculty(term, faculty, deloadInstance);
        this.saveDeloadInstance(deloadInstance);
    }


        /* Find faculty that are available at these time slots
    public ArrayList<FacultyLoad> facultyRuleChecking(char day1, char day2, String startTime, String endTime)
    {
        Iterator<FacultyLoad> allLoads = this.retrieveAllFacultyLoadByTerm(2016, 2017, 1);
        ArrayList<CourseOffering> allCourses = courseOfferingRepository.findAllByStartAYAndEndAYAndTerm(2016, 2017, 1);
        ArrayList<CourseOffering> evaluatedCourses = new ArrayList<>();
        ArrayList<FacultyLoad> evaluatedLoads = new ArrayList<>();
        //get all faculty loads for this term
        FacultyLoad currentLoad;
        /* Checks course offerings that are free at this time slot
        while(allLoads.hasNext())//As long as there are faculty loads
        {
            currentLoad = allLoads.next();//Get the next element
            if(!(currentLoad.getTotalLoad() >= 12) && !(currentLoad.getTotalLoad() + 3 > 12))//remove those that are above 12 units
                evaluatedLoads.add(currentLoad);
        }

        if(evaluatedLoads.size() > 0) {
            //Get arraylist of all courses that faculty is teaching this term
            for (FacultyLoad fc : evaluatedLoads)
                for (CourseOffering cs : allCourses)
                {
                    if(cs.getFaculty() != null)
                    {
                        if (cs.getFaculty().getUserId() == fc.getFaculty().getUserId())
                            evaluatedCourses.add(cs);
                    }
                }


            System.out.println(evaluatedCourses.size());
            allCourses.clear();

            for (CourseOffering cs : evaluatedCourses) {
                for (Days s : cs.getDaysSet()) {//for each day that the course is in
                    if (s.getclassDay() == day1 || s.getclassDay() == day2)//if equal ng day
                    {
                        //System.out.println("First Start time and End time: " + Integer.parseInt(startTime) + " " + Integer.parseInt(endTime));
                        System.out.println("Second Start time and End time: " + s.getbeginTime().replace(":", "") + " " + s.getendTime().replace(":", ""));
                        //Just remove faculty loads that are conflicting with this sched
                        if(!s.getbeginTime().equals(":") && !s.getendTime().equals(":") && !s.getbeginTime().equals("") && !s.getendTime().equals("") && !startTime.equals("") && !endTime.equals("")) {
                            if (conflictsWith(Integer.parseInt(startTime), Integer.parseInt(endTime),
                                    Integer.parseInt(s.getbeginTime().replace(":", "")), Integer.parseInt(s.getendTime().replace(":", "")))) {
                                if (cs.getFaculty() != null) {
                                    int iter = findFacultyLoad(evaluatedLoads, cs.getFaculty().getUserId());//find faculty load
                                    if (iter != -1)
                                        evaluatedLoads.remove(iter);//remove associated faculty load
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(evaluatedLoads.size());
            for(FacultyLoad fl: evaluatedLoads)
                System.out.println(fl.getFaculty().getUserId());
        }
        //process time slots
        return evaluatedLoads;
    } */
}
