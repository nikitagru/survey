package com.nikitagru.controllers;

import com.nikitagru.entities.Question;
import com.nikitagru.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "/create/defaultquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createDefaultQuestion(@RequestParam(value = "surveyname") String surveyName,
                                        @RequestParam(value = "text") String questionText) {
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setCheckBoxQuestion(false);
        question.setRadioButtonsQuestion(false);

        questionService.saveQuestion(question);
        return "";
    }

    @PostMapping(value = "/create/radioquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createRadioQuestion(@RequestParam(value = "surveyname") String surveyName,
                                      @RequestParam(value = "text") String questionText,
                                      @RequestParam(value = "radio") String radio) {
        return "";
    }

    @PostMapping(value = "/create/checkboxquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String createCheckBoxQuestion(@RequestParam(value = "surveyname") String surveyName,
                                         @RequestParam(value = "text") String questionText,
                                         @RequestParam(value = "checkBox") String checkBox) {
        return "";
    }
}
