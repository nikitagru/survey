package com.nikitagru.controllers;

import com.nikitagru.entities.Survey;
import com.nikitagru.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
public class SurveyController {
    private SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

//    @PostMapping(value = "/create",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String createSurvey(@RequestParam(value = "name") String surveyName,
//                         @RequestParam(value = "description") String surveyDescription,
//                         @RequestParam(value = "start") String startDate,
//                         @RequestParam(value = "end") String endDate) {
//
//        Survey surveyFromDB = surveyService.getSurveyByName(surveyName);
//
//        if (surveyFromDB == null) {
//            Survey survey = new Survey();
//            survey.setName(surveyName);
//            survey.setDescription(surveyDescription);
//            survey.setStartSurvey(startDate);
//            survey.setEndSurvey(endDate);
//            surveyService.save(survey);
//        }
//
//        return "Created!";
//    }
//
//    @PostMapping(value = "/update/name",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String updateName(@RequestParam(value = "currentname") String surveyName,
//                             @RequestParam(value = "newname") String newSurveyName) {
//        Survey survey = surveyService.getSurveyByName(surveyName);
//        if (survey != null) {
//            survey.setName(newSurveyName);
//            surveyService.save(survey);
//        }
//        return "Updated name";
//    }
//
//    @PostMapping(value = "/update/description",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String updateDescription(@RequestParam(value = "name") String surveyName,
//                             @RequestParam(value = "description") String description) {
//        Survey survey = surveyService.getSurveyByName(surveyName);
//        if (survey != null) {
//            survey.setDescription(description);
//            surveyService.save(survey);
//        }
//
//        return "Updated description";
//    }
//
//    @PostMapping(value = "/update/end",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String updateEndDate(@RequestParam(value = "currentname") String surveyName,
//                             @RequestParam(value = "end") String endDate) {
//        Survey survey = surveyService.getSurveyByName(surveyName);
//        if (survey != null) {
//            survey.setEndSurvey(endDate);
//            surveyService.save(survey);
//        }
//
//        return "Updated end date";
//    }
//
//    @PostMapping(value = "/delete",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public String delete(@RequestParam(value = "name") String surveyName) {
//        Survey survey = surveyService.getSurveyByName(surveyName);
//        if (survey != null) {
//            surveyService.delete(survey);
//        }
//        return "Deleted";
//    }

    @PostMapping(value = "/create",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Survey survey) {
        Survey surveyFromDB = surveyService.getSurveyByName(survey.getName());
        if (survey != null) {
            surveyService.delete(survey);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
