package com.nikitagru.controllers;

import com.nikitagru.dto.surveydto.DateDto;
import com.nikitagru.dto.surveydto.DescriptionDto;
import com.nikitagru.dto.surveydto.NameDto;
import com.nikitagru.dto.surveydto.SurveyDto;
import com.nikitagru.entities.Survey;
import com.nikitagru.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {
    private SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping(value = "/create",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody SurveyDto surveyDto) {
        Survey surveyFromDB = surveyService.getSurveyByName(surveyDto.getName());

        if (surveyFromDB == null) {
            Survey survey = new Survey();
            survey.setName(surveyDto.getName());
            survey.setStartSurvey(surveyDto.getStartSurvey());
            survey.setEndSurvey(surveyDto.getEndSurvey());
            survey.setDescription(surveyDto.getDescription());

            surveyService.save(survey);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Current survey exists", HttpStatus.CONFLICT);
    }

    @PostMapping(value = "/update/name",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateName(@RequestBody NameDto nameDto) {
        Survey survey = surveyService.getSurveyByName(nameDto.getCurrentName());

        if (survey != null) {
            survey.setName(nameDto.getNewName());
            surveyService.save(survey);
            return new ResponseEntity<>("Updated name", HttpStatus.OK);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/description",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDescription(@RequestBody DescriptionDto descriptionDto) {
        Survey survey = surveyService.getSurveyByName(descriptionDto.getCurrentName());

        if (survey != null) {
            survey.setDescription(descriptionDto.getNewDescription());
            surveyService.save(survey);
            return new ResponseEntity<>("Updated description", HttpStatus.OK);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/update/end",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEndDate(@RequestBody DateDto dateDto) {
        Survey survey = surveyService.getSurveyByName(dateDto.getName());
        if (survey != null) {
            survey.setEndSurvey(dateDto.getNewEndDate());
            surveyService.save(survey);
            return new ResponseEntity<>("Updated end date", HttpStatus.OK);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable("name") String name) {
        Survey survey = surveyService.getSurveyByName(name);
        if (survey != null) {
            surveyService.delete(survey);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }
}
