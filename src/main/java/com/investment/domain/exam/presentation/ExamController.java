package com.investment.domain.exam.presentation;

import com.investment.domain.exam.presentation.dto.request.CreateExamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createExam(@RequestBody @Valid CreateExamRequest request) {

    }


}
