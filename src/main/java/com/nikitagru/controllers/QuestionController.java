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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * Админский контроллер
 */
@RestController
@RequestMapping(value = "/api/v1/admin/")
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

    /***
     * Создание обычного вопроса
     * @param defaultQuestionDto данные обычного вопроса
     * @return Результат создания вопроса
     */
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
                questionService.saveQuestion(question);
                return new ResponseEntity<>("Question created", HttpStatus.CREATED);
            }

            return new ResponseEntity<>("This question is exist", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    /***
     * Создание вопроса с радио-кнопками
     * @param answerOptionsQuestionDto Данные для вопроса с выбоом ответа
     * @return Результат создание вопроса с радио-кнопкой
     */
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
                questionService.saveQuestion(question);
                return new ResponseEntity<>("Question created", HttpStatus.CREATED);
            }

            return new ResponseEntity<>("This question is exist", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);
    }

    /***
     * Создание вопроса с выбором нескольких ответов
     * @param answerOptionsQuestionDto Данные для создания вопроса с выбором нескольких ответов
     * @return Результат создания вопроса
     */
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
                questionService.saveQuestion(question);
                return new ResponseEntity<>("Question created", HttpStatus.CREATED);
            }

            return new ResponseEntity<>("This question is exist", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Survey with current name doesn't exist", HttpStatus.NOT_FOUND);

    }

    /***
     * Обновить обычный вопрос
     * @param defaultQuestionDto Данные для обновления обычного вопроса
     * @return Результат обновления вопроса
     */
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

    /***
     * Обновить вопрос радио-кнопками
     * @param answerOptionsQuestionDto Данные для обновления вопроса с радио-кнопками
     * @return Результат обновления вопроса
     */
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

    /***
     * Обновление вопроса с множественным выбором ответа
     * @param answerOptionsQuestionDto данные для обновления вопроса с нескольким выбором ответов
     * @return Результат обновления вопроса
     */
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

    /***
     * Удалить обычный вопрос
     * @param defaultQuestionDto Данные для обычного вопроса
     * @return Результат удаления
     */
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

    /***
     * Удаление вопроса с несколькими вариантами ответа
     * @param answerOptionsQuestionDto Данные для вопроса с множественным выбором ответа
     * @return Резульат удаления
     */
    @PostMapping(value = "/delete/answeroptions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRadioQuestion(@RequestBody AnswerOptionsQuestionDto answerOptionsQuestionDto) {
        Survey survey = surveyService.getSurveyByName(answerOptionsQuestionDto.getSurveyName());

        if (survey != null) {
            Question questionFromDb = survey.findQuestion(answerOptionsQuestionDto.getQuestionText());

            if (questionFromDb != null) {
                questionService.delete(questionFromDb);

                return new ResponseEntity<>("Deleted question", HttpStatus.OK);
            }

            return new ResponseEntity<>("Question doesn't found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Survey doesn't found", HttpStatus.NOT_FOUND);
    }
}
