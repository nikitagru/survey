package com.nikitagru.controllers;

import com.nikitagru.dto.surveydto.DateDto;
import com.nikitagru.dto.surveydto.DescriptionDto;
import com.nikitagru.dto.surveydto.NameDto;
import com.nikitagru.dto.surveydto.SurveyDto;
import com.nikitagru.entities.Customer;
import com.nikitagru.entities.Survey;
import com.nikitagru.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * Контроллер опроса
 */
@RestController
@RequestMapping(value = "/api/v1/admin/")
public class SurveyController {
    private SurveyService surveyService;

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /***
     * Создание опроса
     * @param surveyDto Данные для создания опроса
     * @return Результат создания опроса
     */
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

    /***
     * Создание даты начала опроса
     * @param dateDto Данные даты
     * @return Результат создание даты старта
     */
    @PostMapping(value = "/create/startdate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createStartDate(@RequestBody DateDto dateDto) {
        Survey survey = surveyService.getSurveyByName(dateDto.getSurveyName());

        if (survey.getStartSurvey() != null) {
            survey.setStartSurvey(dateDto.getNewDate());
            surveyService.save(survey);

            return new ResponseEntity<>("Start date updated", HttpStatus.OK);
        }

        return new ResponseEntity<>("Start date is exist", HttpStatus.CONFLICT);
    }

    /***
     * Обновить название опроса
     * @param nameDto Данные для обновления названия
     * @return Результат обновления названия
     */
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

    /***
     * Изменение описания опроса
     * @param descriptionDto Данные описания
     * @return Результат изменения описания
     */
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

    /***
     * Изменение даты окончания опроса
     * @param dateDto Данные даты
     * @return Результат обновления даты окончания
     */
    @PostMapping(value = "/update/end",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEndDate(@RequestBody DateDto dateDto) {
        Survey survey = surveyService.getSurveyByName(dateDto.getSurveyName());
        if (survey != null) {
            survey.setEndSurvey(dateDto.getNewDate());
            surveyService.save(survey);
            return new ResponseEntity<>("Updated end date", HttpStatus.OK);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    /***
     * Удаление опроса
     * @param name Название опроса
     * @return Результат удаления
     */
    @PostMapping("/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable("name") String name) {
        Survey survey = surveyService.getSurveyByName(name);
        if (survey != null) {
            surveyService.delete(survey);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    /***
     * Получение всех клиентов, прошедшых опрос не анонимно
     * @param surveyId
     * @return
     */
    @GetMapping(value = "/get/{id}/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> getAllCustomers(@PathVariable("id") Long surveyId) {
        Survey survey = surveyService.getSurveyById(surveyId);

        if (survey != null) {
            List<Customer> customers = (List<Customer>) survey.getCustomers();

            return new ResponseEntity<>(customers, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
