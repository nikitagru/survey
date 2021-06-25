package com.nikitagru.dto.questiondto;

import lombok.Data;

@Data
public class UpdateDefaultQuestionDto {
    private String surveyName;
    private String questionText;
    private String newQuestionText;
}
