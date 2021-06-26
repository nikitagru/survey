package com.nikitagru.dto.questiondto;

import lombok.Data;

/***
 * Данные для обновления обычного вопроса
 */
@Data
public class UpdateDefaultQuestionDto {
    private String surveyName;
    private String questionText;
    private String newQuestionText;
}
