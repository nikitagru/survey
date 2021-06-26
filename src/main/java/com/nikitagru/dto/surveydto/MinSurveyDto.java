package com.nikitagru.dto.surveydto;

import com.nikitagru.entities.Survey;
import lombok.Data;

import java.util.Date;

/***
 * Минимизированные данные опроса
 */
@Data
public class MinSurveyDto {
    private Long id;
    private String name;
    private Date startSurvey;
    private Date endSurvey;
    private String description;

    public void surveyToMinSurveyDto(Survey survey) {
        this.id = survey.getId();
        this.name = survey.getName();
        this.startSurvey = survey.getStartSurvey();
        this.endSurvey = survey.getEndSurvey();
        this.description = survey.getDescription();
    }
}
