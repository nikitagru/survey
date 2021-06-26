package com.nikitagru.services;

import com.nikitagru.entities.Question;
import com.nikitagru.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * Сервис вопросов
 */
@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }
}
