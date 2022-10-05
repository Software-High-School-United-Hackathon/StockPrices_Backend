package com.investment.domain.question.exception;

import com.investment.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class QuestionNotFoundException extends CustomException {

    public QuestionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 Question을 찾을 수 없습니다.");
    }
}
