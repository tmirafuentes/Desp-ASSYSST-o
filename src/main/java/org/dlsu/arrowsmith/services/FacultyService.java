package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.*;
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

    /* Retrieve all Deload Instance per Term */
    public Iterator retrieveAllDeloadInstanceByTerm(int startAY, int endAY, int term) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        return deloadInstances.iterator();
    }

    /* Retrieve all Deload Instance of a specific Faculty in a given term */
    public Iterator retrieveAllDeloadInstanceByFaculty(int startAY, int endAY, int term, User faculty) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByStartAYAndEndAYAndTermAndFaculty(startAY, endAY, term, faculty);
        return deloadInstances.iterator();
    }

    /* Retrieve all Deload Instance per Deloading Type in a given time */
    public Iterator retrieveAllDeloadInstanceByType(int startAY, int endAY, int term, Deloading deloading) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByStartAYAndEndAYAndTermAndDeloading(startAY, endAY, term, deloading);
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
    public Iterator retrieveAllFacultyLoadByTerm(int startAY, int endAY, int term) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStartAYAndEndAYAndTerm(startAY, endAY, term);
        return allLoads.iterator();
    }

    /* Retrieve All Faculty Load Per Department */
    public Iterator retrieveAllFacultyLoadByTerm(int startAY, int endAY, int term, Department department) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStartAYAndEndAYAndTermAndDepartment(startAY, endAY, term, department);
        return allLoads.iterator();
    }

    /* Retrieve All Faculty Load Per College */
    public Iterator retrieveAllFacultyLoadByTerm(int startAY, int endAY, int term, College college) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStartAYAndEndAYAndTermAndCollege(startAY, endAY, term, college);
        return allLoads.iterator();
    }

    /* Retrieve Faculty Load of a Faculty per Term */
    public FacultyLoad retrieveFacultyLoadByFaculty(int startAY, int endAY, int term, User faculty) {
        FacultyLoad facultyLoad = (FacultyLoad) facultyLoadRepository.findFacultyLoadByStartAYAndEndAYAndTermAndFaculty(startAY, endAY, term, faculty);
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
    public void addTotalFacultyLoad(int startAY, int endAY, int term, User faculty) {
        if(checkFacultyInDatabase(startAY, endAY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty);
            double totalLoad = 0.0;
            totalLoad += facultyLoad.getAdminLoad() + facultyLoad.getNonacadLoad() +
                    facultyLoad.getResearchLoad() + facultyLoad.getTeachingLoad();
        }
    }

    /* Set Faculty to Leave in a given Term */
    public void setLeaveFaculty(int startAY, int endAY, int term, User faculty, String leave_type) {
        if(checkFacultyInDatabase(startAY, endAY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty);
            facultyLoad.setOnLeave(true);
            facultyLoad.setLeaveType(leave_type);
        }
    }

    /* Assign Academic Load to a Faculty in a given Term */
    public void assignAcademicLoadToFaculty(int startAY, int endAY, int term, User faculty, CourseOffering courseOffering) {
        /* Assign Faculty to Course Offering */
        if(checkFacultyInDatabase(startAY, endAY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty);
            facultyLoad.setTeachingLoad(facultyLoad.getTeachingLoad() + courseOffering.getCourse().getUnits());
            facultyLoadRepository.save(facultyLoad);
        }
    }

    /* Assign Deloading Load to a Faculty in a given Term */
    public void assignResearchLoadToFaculty(int startAY, int endAY, int term, User faculty, DeloadInstance deloadingInstance) {
        if(checkFacultyInDatabase(startAY, endAY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty);
            facultyLoad.setResearchLoad(facultyLoad.getResearchLoad() + deloadingInstance.getDeloading().getUnits());
            facultyLoad.setDeloadedLoad(facultyLoad.getDeloadedLoad() + deloadingInstance.getDeloading().getUnits());
        }
    }
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

    /**
     **
     ** OTHER FUNCTIONS
     **
     */

    public boolean checkFacultyInDatabase(int startAY, int endAY, int term, User faculty) {
        if (retrieveFacultyLoadByFaculty(startAY, endAY, term, faculty) != null)
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


}
