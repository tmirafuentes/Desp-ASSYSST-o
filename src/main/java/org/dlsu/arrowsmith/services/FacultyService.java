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
    private CollegeRepository collegeRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private DeloadingRepository deloadingRepository;
    @Autowired
    private DeloadInstanceRepository deloadInstanceRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private FacultyLoadRepository facultyLoadRepository;

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

    public Iterator retrieveDeloadingByDeloadType(String deloadType)
    {
        return deloadingRepository.findAllByDeloadType(deloadType).iterator();
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

    /* Retrieve a Deload Instance through ID */
    public DeloadInstance retrieveDeloadInstanceByID(Long id) { return deloadInstanceRepository.findByDeloadInId(id); }

    /* Retrieve all Deload Instance of a specific Faculty in a given term */
    public Iterator retrieveAllDeloadInstanceByFaculty(Term term, User faculty) {
        ArrayList<DeloadInstance> deloadInstances = deloadInstanceRepository.findAllByTermAndFaculty(term, faculty);
        return deloadInstances.iterator();
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

    /* Retrieve All Departments by College */
    public Iterator retrieveAllDepartmentsByCollege(College college)
    {
        return departmentRepository.findAllByCollege(college).iterator();
    }

    /* Retrieve a Department through its code */
    public Department retrieveDepartmentByDeptCode(String deptCode)
    {
        return departmentRepository.findDepartmentByDeptCode(deptCode);
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

    /* Retrieve all Faculty Load of a specific Faculty */
    public Iterator retrieveAllFacultyLoadByFaculty(User faculty) {
        ArrayList<FacultyLoad> allLoads = (ArrayList<FacultyLoad>) facultyLoadRepository.findAllByFaculty(faculty);
        return allLoads.iterator();
    }

    /* Retrieve Faculty Load of a Faculty per Term */
    public FacultyLoad retrieveFacultyLoadByFaculty(Term term, User faculty) {
        FacultyLoad facultyLoad = (FacultyLoad) facultyLoadRepository.findFacultyLoadByTermAndFaculty(term, faculty);
        return facultyLoad;
    }

    /* Retrieve All Faculty Load Per Department */
    public Iterator retrieveAllFacultyLoadByTerm(Term term, Department department) {
        ArrayList<FacultyLoad> allLoads = facultyLoadRepository.findAllByTermAndDepartment(term, department);
        return allLoads.iterator();
    }

    /**
     **
     ** SYSTEM FUNCTIONS
     **
     */

    /* TODO: Set Faculty to Leave in a given Term
    public void assignFacultyOnLeave(Term term, User faculty, String leave_type) {
        if(checkFacultyInDatabase(term, faculty)) {
            FacultyLoad facultyLoad = (FacultyLoad) retrieveFacultyLoadByFaculty(term, faculty);
            facultyLoad.setOnLeave(true);
            facultyLoad.setLeaveType(leave_type);
        }
    } */

    /* Assign Research or Administrative Load to a Faculty in a given Term */
    public void assignDeloadingLoadToFaculty(Term term, User faculty, Deloading deloading)
    {
        FacultyLoad facultyLoad = retrieveFacultyLoadByFaculty(term, faculty);
        if(deloading.getDeloadType().equals("AL"))
            facultyLoad.setAdminLoad(facultyLoad.getAdminLoad() + deloading.getUnits());
        else if(deloading.getDeloadType().equals("RL"))
            facultyLoad.setResearchLoad(facultyLoad.getResearchLoad() + deloading.getUnits());

        saveFacultyLoad(facultyLoad);
    }

    /* Assign Academic Load to a Faculty in a given Term */
    public void assignAcademicLoadToFaculty(Term term, User faculty, double units)
    {
        /* Assign Faculty to Course Offering */
        FacultyLoad facultyLoad = retrieveFacultyLoadByFaculty(term, faculty);
        facultyLoad.setTeachingLoad(facultyLoad.getTeachingLoad() + units);
        updateFacultyPreparations(term, faculty, facultyLoad);
    }

    /* Get unique number of courses taught by Faculty in a given Term */
    public void updateFacultyPreparations(Term term, User faculty, FacultyLoad facultyLoad)
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
        facultyLoad.setPreparations(uniqueOfferings.size());
        saveFacultyLoad(facultyLoad);
    }

    /**
     **
     ** OTHER FUNCTIONS
     **
     */
    
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
}
