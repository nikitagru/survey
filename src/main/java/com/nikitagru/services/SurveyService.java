package com.nikitagru.services;

import com.nikitagru.entities.Survey;
import com.nikitagru.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {
    private SurveyRepository surveyRepository;

    @Autowired
    public void setSurveyRepository(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    public void save(Survey survey) {
        surveyRepository.save(survey);
    }

    public Survey getSurveyByName(String name) {
        return surveyRepository.findByName(name);
    }

    public void delete(Survey survey) {
        surveyRepository.delete(survey);
    }
}
