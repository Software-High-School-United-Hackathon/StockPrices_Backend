package com.investment.domain.question.domain.entity;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.news.domain.entity.News;
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
    @JoinColumn(name = "fk_exam_id", nullable = false)
    private Exam exam;

    @Column(nullable = false)
    private int answer;

    private int rightAnswer;

    @Column(nullable = false)
    private String explanation;

    @Column(nullable = false)
    private String image;

    private int score;

    @Column(nullable = false)
    private String uniqueCode;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_news_id", nullable = false)
    private News news;

    public void updateAnswerAndScore(int rightAnswer) {
        this.rightAnswer = rightAnswer;
        this.score = calculateScore(rightAnswer, this.answer);
    }

    private int calculateScore(int rightAnswer, int answer) {

        int score = 5;
        answer /= 2;
        if (rightAnswer == answer) {
            score += 5;
        } else if (rightAnswer + answer == 0) {
            score -= 5;
        } else {
            if (Math.abs(rightAnswer + answer) > 10) {
                score += 2.5;
            } else {
                score -= 2.5;
            }
        }

        return score;
    }

    @Builder
    public Question(int answer, Exam exam, String explanation, String image, String uniqueCode, News news) {
        this.answer = answer;
        this.exam = exam;
        this.explanation = explanation;
        this.image = image;
        this.uniqueCode = uniqueCode;
        this.news = news;
    }
}
