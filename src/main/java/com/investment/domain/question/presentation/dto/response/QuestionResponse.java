package com.investment.domain.question.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.investment.domain.news.domain.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
public class QuestionResponse {

    private long id;
    private int answer;
    private int rightAnswer;
    private String explanation;
    private News news;
}
