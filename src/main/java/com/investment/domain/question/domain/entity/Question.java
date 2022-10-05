package com.investment.domain.question.domain.entity;

import com.investment.domain.exam.domain.entity.Exam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String rightAnswer;

    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String uniqueCode;

    public void updateAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    @Builder
    public Question(String answer, Exam exam, String explanation, String image, String uniqueCode) {
        this.answer = answer;
        this.exam = exam;
        this.explanation = explanation;
        this.image = image;
        this.uniqueCode = uniqueCode;
    }
}
