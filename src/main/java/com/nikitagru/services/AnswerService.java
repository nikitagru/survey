package com.nikitagru.services;

import com.nikitagru.entities.Answer;
import com.nikitagru.repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * Сервис ответов
 */
@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
}
