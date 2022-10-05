package com.investment.domain.question.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
public class QuestionResponse {

    private long id;
    private String answer;
    private String rightAnswer;
    private String explanation;
    private boolean isCorrect;
}
