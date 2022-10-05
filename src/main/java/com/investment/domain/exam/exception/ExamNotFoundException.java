package com.investment.domain.exam.exception;

import com.investment.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExamNotFoundException extends CustomException {

    public ExamNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 uuid를 가진 Exam을 찾을 수 없습니다.");
    }
}
