package com.nikitagru.entities.id;

import java.io.Serializable;

/***
 * Id для таблицы с данными о принадлежности клиентов к опросу
 */
public class CustomerSurveyId implements Serializable {
    private long surveyId;
    private long customerId;

    public CustomerSurveyId() {
    }

    public CustomerSurveyId(long surveyId, long customerId) {
        this.surveyId = surveyId;
        this.customerId = customerId;
    }
}
