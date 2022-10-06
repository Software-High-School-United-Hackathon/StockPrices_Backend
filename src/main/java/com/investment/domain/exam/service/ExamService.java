package com.investment.domain.exam.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.domain.type.ExamStatus;
import com.investment.domain.exam.exception.ExamNotFoundException;
import com.investment.domain.exam.presentation.dto.request.CreateExamRequest;
import com.investment.domain.exam.presentation.dto.response.ExamResult;
import com.investment.domain.question.domain.entity.Question;
import com.investment.domain.question.domain.repository.QuestionRepository;
import com.investment.domain.question.presentation.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    @Transactional(rollbackFor = Exception.class)
    public String createExam(CreateExamRequest request) {

        Exam createdExam = Exam.builder()
                .age(request.getAge())
                .name(request.getName())
                .build();

        examRepository.save(createdExam);

        return createdExam.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public ExamResult getResult(String id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(ExamNotFoundException::new);

        List<Question> questionList = questionRepository.findAllByExam(exam);
        List<QuestionResponse> questionResponses = new ArrayList<>();

        int scoreSum = 0;

        for (Question question : questionList) {

            scoreSum += question.getScore();

            questionResponses.add(
                    QuestionResponse.builder()
                            .id(question.getId())
                            .answer(question.getAnswer())
                            .rightAnswer(question.getRightAnswer())
                            .explanation(question.getExplanation())
                            .build()
            );
        }

        exam.modifyStatus(ExamStatus.FINISHED);

        return ExamResult.builder()
                .name(exam.getName())
                .age(exam.getAge())
                .createdAt(exam.getCreatedAt())
                .allQuestionCount(questionList.size())
                .score(scoreSum)
                .questions(questionResponses)
                .build();
    }
}
