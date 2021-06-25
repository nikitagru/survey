package com.nikitagru.repositories;

import com.nikitagru.entities.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, Long> {
    Survey findByName(String name);
}
