package org.dlsu.arrowsmith.repositories;

import org.dlsu.arrowsmith.classes.main.Term;
import org.springframework.data.repository.CrudRepository;

public interface TermRepository extends CrudRepository<Term, Long>
{
    Term findTermByCurrTermIsTrue();
}
