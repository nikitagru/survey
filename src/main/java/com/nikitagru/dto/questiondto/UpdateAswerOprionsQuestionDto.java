package com.nikitagru.dto.questiondto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateAswerOprionsQuestionDto {
    private String surveyName;
    private String questionText;
    private List<String> answers;
    private String newQuestionText;
    private List<String> newAnswers;
}
