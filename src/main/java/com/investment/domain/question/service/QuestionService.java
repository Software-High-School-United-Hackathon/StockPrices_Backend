package com.investment.domain.question.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.exception.ExamNotFoundException;
import com.investment.domain.question.domain.entity.Question;
import com.investment.domain.question.domain.repository.QuestionRepository;
import com.investment.domain.question.exception.QuestionNotFoundException;
import com.investment.domain.question.presentation.dto.request.InsertAnswerRequest;
import com.investment.domain.question.presentation.dto.response.BeforeQuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    @Transactional(rollbackFor = Exception.class)
    public BeforeQuestionResponse getQuestion(String id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(ExamNotFoundException::new);

        // TODO : fastapi 서버 통해서 질문 받아오기

        Question question = Question.builder()
                .answer("test")
                .explanation("test")
                .exam(exam)
                .image("test")
                .uniqueCode("test")
                .build();

        return BeforeQuestionResponse.builder()
                .id(question.getId())
                .image(question.getImage())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertAnswer(InsertAnswerRequest request) {

        Question question = questionRepository.findById(request.getId())
                        .orElseThrow(QuestionNotFoundException::new);

        question.updateAnswer(request.getAnswer());

        questionRepository.save(question);
    }
}
