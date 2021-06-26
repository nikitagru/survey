package com.nikitagru.repositories;

import com.nikitagru.entities.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/***
 * Репозиторий ответов
 */
@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
