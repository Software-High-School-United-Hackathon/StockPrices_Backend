package com.investment.global.exception;

import com.investment.global.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleCustomException(CustomException e) {
        return new ResponseEntity<>(
            new Response(e.getStatus(), e.getMessage()),
                e.getStatus()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(
                new Response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
