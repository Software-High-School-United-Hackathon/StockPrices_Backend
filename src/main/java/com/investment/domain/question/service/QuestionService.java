package com.investment.domain.question.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.exception.ExamNotFoundException;
import com.investment.domain.question.domain.entity.Question;
import com.investment.domain.question.domain.repository.QuestionRepository;
import com.investment.domain.question.presentation.dto.request.CreateQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public void createQuestion(CreateQuestionRequest request) {

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(ExamNotFoundException::new);

        Question createdQuestion = Question.builder()
                .exam(exam)
                .answer(request.getAnswer())
                .rightAnswer(request.getRightAnswer())
                .explanation(request.getExplanation())
                .build();

        questionRepository.save(createdQuestion);
    }
}
