package com.nikitagru.controllers;

import com.nikitagru.dto.questiondto.AnswerOptionsQuestionDto;
import com.nikitagru.dto.questiondto.DefaultQuestionDto;
import com.nikitagru.dto.questiondto.UpdateAswerOprionsQuestionDto;
import com.nikitagru.dto.questiondto.UpdateDefaultQuestionDto;
import com.nikitagru.entities.Question;
import com.nikitagru.entities.Survey;
import com.nikitagru.services.QuestionService;
import com.nikitagru.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
    private QuestionService questionService;

    private SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "/create/defaultquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createDefaultQuestion(@RequestBody DefaultQuestionDto defaultQuestionDto) {
        Question question = new Question();
        Survey survey = surveyService.getSurveyByName(defaultQuestionDto.getSurveyName());
        if (survey != null) {

            Question questionFromDb = survey.findQuestion(defaultQuestionDto.getQuestionText());

            if(questionFromDb == null) {
                question.setQuestionText(defaultQuestionDto.getQuestionText());
                question.setCheckBoxQuestion(false);
                question.setRadioButtonsQuestion(false);
                question.setSurvey(survey);
                questionService.saveQuestion(question);
                return new ResponseEntity<>("Question created", HttpStatus.CREATED);
            }

            return new ResponseEntity<>("This question is exist", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create/radioquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createRadioQuestion(@RequestBody AnswerOptionsQuestionDto answerOptionsQuestionDto) {
        Question question = new Question();
        Survey survey = surveyService.getSurveyByName(answerOptionsQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(answerOptionsQuestionDto.getQuestionText());

            if(questionFromDb == null) {
                question.setQuestionText(answerOptionsQuestionDto.getQuestionText());
                question.setRadioButtonsQuestion(true);
                question.setCheckBoxQuestion(false);
                question.setAnswers(answerOptionsQuestionDto.getAnswers());
                question.setSurvey(survey);
                questionService.saveQuestion(question);
                return new ResponseEntity<>("Question created", HttpStatus.CREATED);
            }

            return new ResponseEntity<>("This question is exist", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/create/checkboxquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCheckBoxQuestion(@RequestBody AnswerOptionsQuestionDto answerOptionsQuestionDto) {
        Question question = new Question();
        Survey survey = surveyService.getSurveyByName(answerOptionsQuestionDto.getSurveyName());

        if (survey != null) {

            Question questionFromDb = survey.findQuestion(answerOptionsQuestionDto.getQuestionText());

            if(questionFromDb == null) {
                question.setQuestionText(answerOptionsQuestionDto.getQuestionText());
                question.setRadioButtonsQuestion(false);
                question.setCheckBoxQuestion(true);
                question.setAnswers(answerOptionsQuestionDto.getAnswers());
                question.setSurvey(survey);
                questionService.saveQuestion(question);
                return new ResponseEntity<>("Question created", HttpStatus.CREATED);
            }

            return new ResponseEntity<>("This question is exist", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/update/defaultquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDefaultQuestion(@RequestBody UpdateDefaultQuestionDto defaultQuestionDto) {
        Survey survey = surveyService.getSurveyByName(defaultQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(defaultQuestionDto.getQuestionText());

            if (questionFromDb != null) {
                questionFromDb.setQuestionText(defaultQuestionDto.getNewQuestionText());
                questionService.saveQuestion(questionFromDb);

                return new ResponseEntity<>("Updated question", HttpStatus.OK);
            }

            return new ResponseEntity<>("Question doesn't found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Survey doesn't found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/radioquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRadioQuestion(@RequestBody UpdateAswerOprionsQuestionDto answerOptionsQuestionDto) {
        Survey survey = surveyService.getSurveyByName(answerOptionsQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(answerOptionsQuestionDto.getQuestionText());

            if (questionFromDb != null) {
                questionFromDb.setAnswers(answerOptionsQuestionDto.getNewAnswers());
                questionFromDb.setQuestionText(answerOptionsQuestionDto.getNewQuestionText());

                questionService.saveQuestion(questionFromDb);

                return new ResponseEntity<>("Updated question", HttpStatus.OK);
            }

            return new ResponseEntity<>("Question doesn't found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Survey doesn't found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/checkboxquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateCheckboxQuestion(@RequestBody UpdateAswerOprionsQuestionDto answerOptionsQuestionDto) {
        Survey survey = surveyService.getSurveyByName(answerOptionsQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(answerOptionsQuestionDto.getQuestionText());

            if (questionFromDb != null) {
                questionFromDb.setAnswers(answerOptionsQuestionDto.getNewAnswers());
                questionFromDb.setQuestionText(answerOptionsQuestionDto.getNewQuestionText());

                questionService.saveQuestion(questionFromDb);

                return new ResponseEntity<>("Updated question", HttpStatus.OK);
            }

            return new ResponseEntity<>("Question doesn't found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Survey doesn't found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/delete/defaultquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteDefault(@RequestBody DefaultQuestionDto defaultQuestionDto) {
        Survey survey = surveyService.getSurveyByName(defaultQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(defaultQuestionDto.getQuestionText());

            if (questionFromDb != null) {
                questionService.delete(questionFromDb);

                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            }

            return new ResponseEntity<>("Question doesn't found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Survey doesn't found", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/delete/radioquestion",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRadioQuestion(@RequestBody AnswerOptionsQuestionDto answerOptionsQuestionDto) {
        Survey survey = surveyService.getSurveyByName(answerOptionsQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(answerOptionsQuestionDto.getQuestionText());

            if (questionFromDb != null) {
                questionFromDb.setAnswers(answerOptionsQuestionDto.getAnswers());
                questionFromDb.setQuestionText(answerOptionsQuestionDto.getQuestionText());

                questionService.saveQuestion(questionFromDb);

                return new ResponseEntity<>("Updated question", HttpStatus.OK);
            }

            return new ResponseEntity<>("Question doesn't found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Survey doesn't found", HttpStatus.NOT_FOUND);
    }
}
