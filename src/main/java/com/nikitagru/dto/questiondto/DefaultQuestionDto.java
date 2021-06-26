package com.nikitagru.dto.questiondto;

import lombok.Data;

/***
 * Данные обычного вопроса
 */
@Data
public class DefaultQuestionDto {
    private String surveyName;
    private String questionText;
}
