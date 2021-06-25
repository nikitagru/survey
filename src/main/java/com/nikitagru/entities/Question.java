package com.nikitagru.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
//
//    private List<String> answerOprions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    private Survey survey;

    public Long getSurveyId() {
        return survey.getId();
    }
}
