package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.DeloadInstance;
import org.dlsu.arrowsmith.classes.Deloading;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DeloadInstanceRepository extends CrudRepository<DeloadInstance, Long> {
    ArrayList<DeloadInstance> findAllByStart_AYAndEnd_AYAndTerm(int start_AY, int end_AY, int term);
    ArrayList<DeloadInstance> findAllByStart_AYAndEnd_AYAndTermAndFaculty(int start_AY, int end_AY, int term, User faculty);
    ArrayList<DeloadInstance> findAllByStart_AYAndEnd_AYAndTermAndDeloading(int start_AY, int end_AY, int term, Deloading deloading);
}
