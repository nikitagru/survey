package com.nikitagru.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
}
