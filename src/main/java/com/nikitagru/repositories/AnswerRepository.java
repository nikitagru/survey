package com.nikitagru.repositories;

import com.nikitagru.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/***
 * Репозиторий ответов
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
