package com.nikitagru.dto.questiondto;

import lombok.Data;

import java.util.List;

/***
 * Данные вопроса с вариантами ответа
 */
@Data
public class AnswerOptionsQuestionDto {
    private String surveyName;
    private String questionText;
    private List<String> answers;
}
