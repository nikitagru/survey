package com.nikitagru.dto.questiondto;

import com.nikitagru.entities.Question;
import com.nikitagru.entities.Survey;
import lombok.Data;

@Data
public class DefaultQuestionDto {
    private String surveyName;
    private String questionText;

    public Question questionDtoToQuestion(Survey survey) {
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setSurvey(survey);
        question.setRadioButtonsQuestion(false);
        question.setCheckBoxQuestion(false);

        return question;
    }
}
