package com.investment.domain.exam.presentation;

import com.investment.domain.exam.presentation.dto.request.CreateExamRequest;
import com.investment.domain.exam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createExam(@RequestBody @Valid CreateExamRequest request) {
        return examService.createExam(request);
    }


}
