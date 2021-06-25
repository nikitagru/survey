package com.nikitagru.dto.questiondto;

import com.nikitagru.entities.Question;
import com.nikitagru.entities.Survey;
import lombok.Data;

import java.util.List;

@Data
public class AnswerOptionsQuestionDto {
    private String surveyName;
    private String questionText;
    private List<String> answers;

    public Question answerOptionsQuestionDtoToQuestion(Survey survey, boolean isRadio) {
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setRadioButtonsQuestion(isRadio);
        question.setCheckBoxQuestion(isRadio);
        question.setAnswers(answers);
        question.setSurvey(survey);

        return question;
    }
}
