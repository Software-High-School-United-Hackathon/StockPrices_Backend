package com.investment.domain.exam.domain.entity;

import com.investment.domain.exam.domain.type.ExamStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "exam")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exam {

    @Id
    private String id;

    @Column(nullable = false)
    @Size(max = 100)
    private String name;

    @Column(nullable = false)
    private int age;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ExamStatus status;

    private String tendency;

    private String tendencyExplanation;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void modifyStatus(ExamStatus status) {
        this.status = status;
    }

    public void addTendency(String tendency, String tendencyExplanation) {
        this.tendency = tendency;
        this.tendencyExplanation = tendencyExplanation;
    }

    @Builder
    public Exam(String name, int age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.status = ExamStatus.PENDING;
    }
}
