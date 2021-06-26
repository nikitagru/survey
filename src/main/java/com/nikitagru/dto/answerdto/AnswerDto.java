package com.nikitagru.dto.answerdto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long surveyId;
    private Long questionId;
    private String answer;
    private Long customerId;
}
