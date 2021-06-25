package com.nikitagru.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private boolean radioButtonsQuestion;
    private boolean checkBoxQuestion;

    private String answers;

    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    private Survey survey;

    public Long getSurveyId() {
        return survey.getId();
    }

    public String[] getAnswers() {
        String[] answersArray = answers.split("\n");
        return answersArray;
    }

    public void setAnswers(List<String> answers) {
        StringBuffer stringBuffer = new StringBuffer();

        for (String answer : answers) {
            stringBuffer.append(answer + "\n");
        }

        this.answers = stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return radioButtonsQuestion == question.radioButtonsQuestion && checkBoxQuestion == question.checkBoxQuestion && questionText.equals(question.questionText) && survey.equals(question.survey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, radioButtonsQuestion, checkBoxQuestion, survey);
    }
}
