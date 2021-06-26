package com.nikitagru.dto.answerdto;

import com.nikitagru.entities.Answer;
import lombok.Data;

/***
 * Минимизированная вариация данных ответа
 */
@Data
public class MinAnswerDto {
    private Long id;
    private Long customerId;
    private Long surveyId;
    private Long questionId;
    private String text;

    public void answerToAnswerDto(Answer answer) {
        this.id = answer.getId();
        this.customerId = answer.getCustomer().getId();
        this.surveyId = answer.getSurvey().getId();
        this.questionId = answer.getQuestion().getId();
        this.text = answer.getAnswerText();
    }
}
