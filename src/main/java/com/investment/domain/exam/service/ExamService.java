package com.investment.domain.exam.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.presentation.dto.request.CreateExamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public String createExam(CreateExamRequest request) {

        Exam exam = Exam.builder()
                .age(request.getAge())
                .name(request.getName())
                .build();

        examRepository.save(exam);

        return exam.getId();
    }
}
