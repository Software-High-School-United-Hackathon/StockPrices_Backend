package com.investment.domain.question.domain.repository;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.question.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByExam(Exam exam);
}
