package com.investment.domain.question.presentation;

import com.investment.domain.question.presentation.dto.request.InsertAnswerRequest;
import com.investment.domain.question.presentation.dto.response.BeforeQuestionResponse;
import com.investment.domain.question.presentation.dto.response.QuestionResponse;
import com.investment.domain.question.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "질문 조회(제공)", notes = "외부 질문 서버를 통해 examId를 이용해 질문을 제공받습니다.")
    public BeforeQuestionResponse getQuestion(@RequestParam String id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "id로 단일 질문 조회", notes = "Question의 id 값으로 질문 조회")
    public QuestionResponse getQuestionById(@PathVariable long id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/wrong")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "examId로 틀린 질문들 조회", notes = "examId로 틀린 질문들 (score 값이 4 이하인 질문들) 조회")
    public List<QuestionResponse> getWrongQuestions(@RequestParam String id) {
        return questionService.getWrongQuestions(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "질문 응답", notes = "질문에 대한 답변을 생성합니다.")
    public void createQuestion(@RequestBody @Valid InsertAnswerRequest request) {
        questionService.insertAnswer(request);
    }
}
