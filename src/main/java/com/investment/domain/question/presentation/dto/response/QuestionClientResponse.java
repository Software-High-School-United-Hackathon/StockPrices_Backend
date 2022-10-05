package com.investment.domain.question.presentation.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionClientResponse {

    private String image;
    private String answer;
    private String code;
    private String explanation;
}
