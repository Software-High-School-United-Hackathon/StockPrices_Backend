package com.investment.domain.question.presentation;

import com.investment.domain.question.presentation.dto.request.CreateQuestionRequest;
import com.investment.domain.question.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "질문/응답 생성", notes = "fastapi 서버를 통해서 받은 질문과 그에 대한 답변을 생성합니다.")
    public void createQuestion(@RequestBody @Valid CreateQuestionRequest request) {

    }
}
