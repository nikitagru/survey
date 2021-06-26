package com.nikitagru.entities;

import com.nikitagru.entities.id.CustomerSurveyId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "customers_surveys")
@IdClass(CustomerSurveyId.class)
public class CustomerSurvey {
    @Id
    private Long surveyId;
    @Id
    private Long customerId;
}
