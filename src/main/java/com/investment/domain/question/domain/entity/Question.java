package com.investment.domain.question.domain.entity;

import com.investment.domain.exam.domain.entity.Exam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_exam_id")
    private Exam exam;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private String rightAnswer;

    @Column(nullable = false)
    private String explanation;

    @Builder
    public Question(String answer, String rightAnswer, Exam exam, String explanation) {
        this.answer = answer;
        this.rightAnswer = rightAnswer;
        this.exam = exam;
        this.explanation = explanation;
    }
}
