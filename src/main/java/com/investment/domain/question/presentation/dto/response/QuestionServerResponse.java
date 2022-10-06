package com.investment.domain.question.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestionServerResponse {

    private String image;
    private int answer;
    private String code;
    private String stock;
    private String explanation;
    private String newsTitle;
    private String newsImgUrl;
    private String newsArticle;
}
