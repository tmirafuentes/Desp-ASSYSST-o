package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.main.*;
import org.dlsu.arrowsmith.repositories.ConcernRepository;
import org.dlsu.arrowsmith.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ConcernsService
{
    /* Repositories */
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConcernRepository concernRepository;

    /**
     **
     ** CONCERNS
     ** CRUD FUNCTIONS
     **
     */

    /* Create/Update Concerns */
    public Concern saveConcern(Concern concern) {
        return concernRepository.save(concern);
    }

    /* Retrieve Individual Concern */
    public Concern findConcernByConcernId(Long concernId)
    {
        return concernRepository.findConcernByConcernId(concernId);
    }

    /* Retrieve All Concerns By Sender */
    public Iterator retrieveAllConcernsBySender(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllBySenderOrderByDateTimeCommittedDesc(user);
        return concerns.iterator();
    }

    /* Retrieve All Concerns By Receiver */
    public Iterator retrieveAllConcernsByReceiver(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiverOrderByDateTimeCommittedDesc(user);
        return concerns.iterator();
    }

    public int retrieveNumberConcernsByBoolean(User user, boolean ack)
    {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiverAndAcknowledged(user, ack);
        return concerns.size();
    }

    /* Retrieve All Unacknowledged Concerns By Receiver */
    public boolean retrieveAllUnacknowledgedConcernsByReceiver(User user, boolean ack, CourseOffering courseOffering)
    {
        Iterator concerns = concernRepository.findAllByReceiverAndAcknowledged(user, ack).iterator();
        while(concerns.hasNext())
        {
            Concern concern = (Concern) concerns.next();
            if(concern.getSubject().equals(courseOffering.getCourse().getCourseCode() + " " + courseOffering.getSection()))
                return true;
        }
        return false;
    }

    /* Retrieve All Concerns By Receiver or Sender */
    public Iterator retrieveAllConcernsByUser(User sender, User receiver) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllBySenderOrReceiver(sender, receiver);
        return concerns.iterator();
    }

    /* Retrieve Department Head through department */
    public User retrieveDepartmentHead(Department department, String userType)
    {
        return userRepository.findUserByDepartmentAndUserType(department, userType);
    }

    /* Retrieve Department Head through department */
    public User retrieveAcadAssistant(College college)
    {
        return userRepository.findUserByCollegeAndUserType(college, "Academic Programming Officer");
    }

    public Iterator retrievePartialConcernsByReceiver(User receiver)
    {
        ArrayList<Concern> partialConcerns = concernRepository.findAllByReceiverOrderByDateTimeCommittedDesc(receiver);
        return partialConcerns.iterator();
    }
}
