package com.nikitagru.dto.surveydto;

import lombok.Data;

import java.util.Date;

@Data
public class SurveyDto {
    private String name;
    private Date startSurvey;
    private Date endSurvey;
    private String description;
}
