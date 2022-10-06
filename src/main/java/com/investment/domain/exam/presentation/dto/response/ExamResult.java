package com.investment.domain.exam.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.investment.domain.question.presentation.dto.response.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
public class ExamResult {

    private String name;
    private int age;
    private LocalDateTime createdAt;
    private int allQuestionCount;
    private int score;
    private String tendency;
    private String tendencyExplanation;
    private List<QuestionResponse> questions;
}
