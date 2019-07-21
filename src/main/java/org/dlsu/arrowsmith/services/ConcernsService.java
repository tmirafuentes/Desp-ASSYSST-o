package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.main.College;
import org.dlsu.arrowsmith.classes.main.Concern;
import org.dlsu.arrowsmith.classes.main.Department;
import org.dlsu.arrowsmith.classes.main.User;
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
    public void saveConcern(Concern concern) {
        concernRepository.save(concern);
    }

    /* Retrieve Individual Concern */
    public Concern findConcernByConcernId(Long concernId)
    {
        return concernRepository.findConcernByConcernId(concernId);
    }

    /* Retrieve All Concerns By Sender */
    public Iterator retrieveAllConcernsBySender(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllBySenderOrderByDateTimeCommittedAsc(user);
        return concerns.iterator();
    }

    /* Retrieve All Concerns By Receiver */
    public Iterator retrieveAllConcernsByReceiver(User user) {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiverOrderByDateTimeCommittedAsc(user);
        return concerns.iterator();
    }

    public int retrieveNumberConcernsByBoolean(User user, boolean ack)
    {
        ArrayList<Concern> concerns = (ArrayList<Concern>) concernRepository.findAllByReceiverAndAcknowledged(user, ack);
        return concerns.size();
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
        ArrayList<Concern> partialConcerns = concernRepository.findAllByReceiverOrderByDateTimeCommittedAsc(receiver);
        return partialConcerns.iterator();
    }
}
