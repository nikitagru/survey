package com.nikitagru.controllers;

import com.nikitagru.dto.answerdto.AnswerDto;
import com.nikitagru.dto.answerdto.CustomerDto;
import com.nikitagru.dto.answerdto.CustomersAnswersDto;
import com.nikitagru.dto.answerdto.MinAnswerDto;
import com.nikitagru.dto.surveydto.MinSurveyDto;
import com.nikitagru.entities.Answer;
import com.nikitagru.entities.Customer;
import com.nikitagru.entities.Question;
import com.nikitagru.entities.Survey;
import com.nikitagru.services.AnswerService;
import com.nikitagru.services.CustomerService;
import com.nikitagru.services.QuestionService;
import com.nikitagru.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customer/")
public class CustomerController {
    private SurveyService surveyService;
    private QuestionService questionService;
    private CustomerService customerService;
    private AnswerService answerService;

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping(value = "/getall",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MinSurveyDto>> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        List<Survey> notOverDueSurveys = new ArrayList<>();

        List<MinSurveyDto> minSurveyDtos = new ArrayList<>();

        Date currentDate = new Date();

        for (Survey survey : surveys) {
            if (survey.getEndSurvey().after(currentDate)) {
                notOverDueSurveys.add(survey);
                MinSurveyDto minSurveyDto = new MinSurveyDto();
                minSurveyDto.surveyToMinSurveyDto(survey);
                minSurveyDtos.add(minSurveyDto);
            }
        }

        return new ResponseEntity<>(minSurveyDtos, HttpStatus.FOUND);
    }

    @GetMapping(value = "/get/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MinSurveyDto> getSurvey(@PathVariable("id") Long id) {
        Survey survey = surveyService.getSurveyById(id);

        if (survey != null) {
            Date currentDate = new Date();

            if (survey.getEndSurvey().after(currentDate)) {
                MinSurveyDto minSurveyDto = new MinSurveyDto();
                minSurveyDto.surveyToMinSurveyDto(survey);
                return new ResponseEntity<>(minSurveyDto, HttpStatus.FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/answer",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAnswer(@RequestBody AnswerDto answerDto) {
        Answer answer = new Answer();
        Customer customer = customerService.getCustomerById(answerDto.getCustomerId());
        Question question = questionService.getQuestionById(answerDto.getQuestionId());
        if (question != null) {
            Survey survey = surveyService.getSurveyById(answerDto.getSurveyId());

            if (survey != null) {
                Date currentDate = new Date();

                if (survey.getEndSurvey().after(currentDate)) {
                    answer.setQuestion(question);
                    answer.setCustomer(customer);
                    answer.setSurvey(survey);
                    answer.setAnswerText(answerDto.getAnswer());

                    answerService.saveAnswer(answer);

                    return new ResponseEntity<>("Answer Saved", HttpStatus.OK);
                }

                return new ResponseEntity<>("Time is out", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>("Current survey doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Current question or customer doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/save/{id}")
    public ResponseEntity<String> saveCustomerData(@PathVariable("id") Long surveyId, @RequestBody CustomerDto customerDto) {
        Customer customer = customerDto.customerDtoToCustomer();
        Survey survey = surveyService.getSurveyById(surveyId);
        if (survey != null) {
            if (customer != null) {
                customerService.saveCustomerAndAddToSurvey(surveyId, customer);
                return new ResponseEntity<>("Saved data", HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid unique data", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Current survey doesn't exist", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get/answers/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MinAnswerDto>> getAllAnswersBySurvey(@PathVariable("id") Long id,
                                                                    @RequestBody CustomersAnswersDto customersAnswersDto) {
        List<MinAnswerDto> currentCustomersAnswers = new ArrayList<>();

        Survey currentSurvey = surveyService.getSurveyById(id);
        Customer customer = customerService.getCustomerById(customersAnswersDto.getCustomerId());

        if (customer != null && currentSurvey != null) {
            for (Answer answer : customer.getAnswers()) {
                if (answer.getSurvey().equals(currentSurvey)) {
                    MinAnswerDto minAnswerDto = new MinAnswerDto();
                    minAnswerDto.answerToAnswerDto(answer);
                    currentCustomersAnswers.add(minAnswerDto);
                }
            }

            return new ResponseEntity<>(currentCustomersAnswers, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/get/answers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MinAnswerDto>> getAllSurveysAndAnswers(@RequestBody CustomersAnswersDto customersAnswersDto) {
        List<MinAnswerDto> result = new ArrayList<>();

        Customer customer = customerService.getCustomerById(customersAnswersDto.getCustomerId());

        if (customer != null) {

            for (Survey survey : customer.getSurveys()) {
                for (Answer answer : customer.getAnswers()) {
                    if (survey.equals(answer.getSurvey())) {
                        MinAnswerDto minAnswerDto = new MinAnswerDto();
                        minAnswerDto.answerToAnswerDto(answer);
                        result.add(minAnswerDto);
                    }
                }
            }

            return new ResponseEntity<>(result, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
