package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.*;
import org.dlsu.arrowsmith.models.Faculty;
import org.dlsu.arrowsmith.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Iterator retrieveAllDeloadInstanceByTerm(int start_AY, int end_AY, int term) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByStart_AYAndEnd_AYAndTerm(start_AY, end_AY, term);
        return deloadInstances.iterator();
    }

    /* Retrieve all Deload Instance of a specific Faculty in a given term */
    public Iterator retrieveAllDeloadInstanceByFaculty(int start_AY, int end_AY, int term, User faculty) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByStart_AYAndEnd_AYAndTermAndFaculty(start_AY, end_AY, term, faculty);
        return deloadInstances.iterator();
    }

    /* Retrieve all Deload Instance per Deloading Type in a given time */
    public Iterator retrieveAllDeloadInstanceByType(int start_AY, int end_AY, int term, Deloading deloading) {
        ArrayList<DeloadInstance> deloadInstances = (ArrayList<DeloadInstance>) deloadInstanceRepository.findAllByStart_AYAndEnd_AYAndTermAndDeloading(start_AY, end_AY, term, deloading);
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

    /* Retrieve All Load Per Term */
    public Iterator retrieveAllFacultyLoadByTerm(int start_AY, int end_AY, int term) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStart_AYAndEnd_AYAndTerm(start_AY, end_AY, term);
        return allLoads.iterator();
    }

    /* Retrieve All Faculty Load Per Department */
    public Iterator retrieveAllFacultyLoadByTerm(int start_AY, int end_AY, int term, Department department) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStart_AYAndEnd_AYAndTermAndDepartment(start_AY, end_AY, term, department);
        return allLoads.iterator();
    }

    /* Retrieve All Faculty Load Per College */
    public Iterator retrieveAllFacultyLoadByTerm(int start_AY, int end_AY, int term, College college) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStart_AYAndEnd_AYAndTermAndCollege(start_AY, end_AY, term, college);
        return allLoads.iterator();
    }

    /* Retrieve Faculty Load of a Faculty per Term */
    public Iterator retrieveFacultyLoadByFaculty(int start_AY, int end_AY, int term, User faculty) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByStart_AYAndEnd_AYAndTermAndFaculty(start_AY, end_AY, term, faculty);
        return allLoads.iterator();
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */

    /* Summation of Faculty Load in a given Term */
    public void addTotalFacultyLoad(int start_AY, int end_AY, int term, User faculty) {
        if(checkFacultyInDatabase(start_AY, end_AY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(start_AY, end_AY, term, faculty).next();
            double totalLoad = 0.0;
            totalLoad += facultyLoad.getAdmin_load() + facultyLoad.getNonacad_load() +
                    facultyLoad.getResearch_load() + facultyLoad.getTeaching_load();
        }
    }

    /* Set Faculty to Leave in a given Term */
    public void setLeaveFaculty(int start_AY, int end_AY, int term, User faculty, String leave_type) {
        if(checkFacultyInDatabase(start_AY, end_AY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(start_AY, end_AY, term, faculty).next();
            facultyLoad.setOn_leave(true);
            facultyLoad.setLeave_type(leave_type);
        }
    }

    /* Assign Academic Load to a Faculty in a given Term */
    public void assignAcademicLoadToFaculty(int start_AY, int end_AY, int term, User faculty, CourseOffering courseOffering) {
        /* Assign Faculty to Course Offering */
        if(checkFacultyInDatabase(start_AY, end_AY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(start_AY, end_AY, term, faculty).next();
            facultyLoad.setTeaching_load(facultyLoad.getTeaching_load() + courseOffering.getCourse().getUnits());
            facultyLoadRepository.save(facultyLoad);
        }
    }

    /* Assign Deloading Load to a Faculty in a given Term */
    public void assignResearchLoadToFaculty(int start_AY, int end_AY, int term, User faculty, DeloadInstance deloadingInstance) {
        if(checkFacultyInDatabase(start_AY, end_AY, term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(start_AY, end_AY, term, faculty).next();
            facultyLoad.setResearch_load(facultyLoad.getResearch_load() + deloadingInstance.getDeloading().getUnits());
            facultyLoad.setDeloaded_load(facultyLoad.getDeloaded_load() + deloadingInstance.getDeloading().getUnits());
        }
    }

    /**
     **
     ** OTHER FUNCTIONS
     **
     */

    public boolean checkFacultyInDatabase(int start_AY, int end_AY, int term, User faculty) {
        if (retrieveFacultyLoadByFaculty(start_AY, end_AY, term, faculty).hasNext())
            return true;
        return false;
    }
}
