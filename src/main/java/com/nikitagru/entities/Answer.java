package com.nikitagru.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "answers")
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "id")
    private Survey survey;

    @OneToOne
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    private Question question;
    private String answerText;
}
