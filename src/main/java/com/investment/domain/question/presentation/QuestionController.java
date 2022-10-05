package com.investment.domain.question.presentation;

import com.investment.domain.question.presentation.dto.request.InsertAnswerRequest;
import com.investment.domain.question.presentation.dto.response.BeforeQuestionResponse;
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

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "질문 조회", notes = "examId를 이용해 질문을 제공받습니다.")
    public BeforeQuestionResponse getQuestion(@RequestParam String id) {
        return questionService.getQuestion(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "질문/응답 생성", notes = "질문과 그에 대한 답변을 생성합니다.")
    public void createQuestion(@RequestBody @Valid InsertAnswerRequest request) {
        questionService.insertAnswer(request);
    }
}
