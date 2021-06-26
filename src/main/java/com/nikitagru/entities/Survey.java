package com.nikitagru.entities;

import lombok.Data;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/***
 * Опрос
 */
@Entity
@Data
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date startSurvey;
    private Date endSurvey;
    private String description;

    @OneToMany(mappedBy = "survey")
    private Collection<Question> questions = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "customers_surveys",
            joinColumns = @JoinColumn(name = "surveyId"),
            inverseJoinColumns = @JoinColumn(name = "customerId"))
    private Collection<Customer> customers = new ArrayList<>();

    public void setStartSurvey(String startSurvey) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            this.startSurvey = format.parse(startSurvey);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setStartSurvey(Date startSurvey) {
        this.startSurvey = startSurvey;
    }

    public void setEndSurvey(String endSurvey) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            this.endSurvey = format.parse(endSurvey);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setEndSurvey(Date endSurvey) {
        this.endSurvey = endSurvey;
    }

    public Question findQuestion(String text) {
        return questions.stream().filter(q -> q.getQuestionText().equals(text)).findFirst().orElse(null);
    }
}
