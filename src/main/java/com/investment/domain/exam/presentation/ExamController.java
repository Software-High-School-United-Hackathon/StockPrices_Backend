package com.investment.domain.exam.presentation;

import com.investment.domain.exam.presentation.dto.request.CreateExamRequest;
import com.investment.domain.exam.presentation.dto.response.ExamResult;
import com.investment.domain.exam.service.ExamService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "투자 시험 생성", notes = "투자 시험을 생성하고 return 값으로 해당 투자 시험에 대한 unique key(uuid)를 얻습니다.")
    public String createExam(@RequestBody @Valid CreateExamRequest request) {
        return examService.createExam(request);
    }

    @GetMapping("/result")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "투자 시험 결과 조회", notes = "uuid로 투자 시험 결과를 조회합니다.")
    public ExamResult getResult(@RequestParam String id) {
        return examService.getResult(id);
    }
}
