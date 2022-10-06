package com.investment.domain.question.exception;

import com.investment.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class QuestionServerException extends CustomException {

    public QuestionServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "외부 질문(Question) 서버와 통신 중 실패했습니다.");
    }
}
