package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.DeloadInstance;
import org.dlsu.arrowsmith.classes.main.Deloading;
import org.dlsu.arrowsmith.classes.main.Term;
import org.dlsu.arrowsmith.classes.main.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface DeloadInstanceRepository extends CrudRepository<DeloadInstance, Long> {
    ArrayList<DeloadInstance> findAllByTerm(Term term);
    ArrayList<DeloadInstance> findAllByTermAndFaculty(Term term, User faculty);
    ArrayList<DeloadInstance> findAllByTermAndDeloading(Term term, Deloading deloading);
}
