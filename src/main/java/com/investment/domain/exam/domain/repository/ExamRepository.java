package com.investment.domain.exam.domain.repository;

import com.investment.domain.exam.domain.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, String> {

}
