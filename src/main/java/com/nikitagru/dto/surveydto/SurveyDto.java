package com.nikitagru.dto.surveydto;

import lombok.Data;

import java.util.Date;

/***
 * Данные опроса
 */
@Data
public class SurveyDto {
    private String name;
    private Date startSurvey;
    private Date endSurvey;
    private String description;
}
