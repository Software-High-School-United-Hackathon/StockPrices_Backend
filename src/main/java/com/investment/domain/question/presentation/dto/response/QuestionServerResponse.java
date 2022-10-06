package com.investment.domain.question.presentation.dto.response;

import lombok.Data;

@Data
public class QuestionServerResponse {

    private String image;
    private int answer;
    private String code;
    private String explanation;
}
