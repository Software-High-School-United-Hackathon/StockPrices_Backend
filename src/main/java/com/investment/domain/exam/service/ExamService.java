package com.investment.domain.exam.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.domain.type.ExamStatus;
import com.investment.domain.exam.exception.ExamNotFoundException;
import com.investment.domain.exam.presentation.dto.request.CreateExamRequest;
import com.investment.domain.exam.presentation.dto.response.ExamResult;
import com.investment.domain.exam.presentation.dto.response.QuestionResultServerResponse;
import com.investment.domain.question.domain.entity.Question;
import com.investment.domain.question.domain.repository.QuestionRepository;
import com.investment.domain.question.exception.QuestionServerException;
import com.investment.domain.question.presentation.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    @Value("${api.question}")
    private String questionApiBaseUrl;

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
        int wrongSum = 0;

        for (Question question : questionList) {

            scoreSum += question.getScore();
            if(question.getScore() < 5) {
                wrongSum++;
            }

            questionResponses.add(
                    QuestionResponse.builder()
                            .id(question.getId())
                            .answer(question.getAnswer())
                            .rightAnswer(question.getRightAnswer())
                            .explanation(question.getExplanation())
                            .score(question.getScore())
                            .stock(question.getName())
                            .news(question.getNews())
                            .build()
            );
        }

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(questionApiBaseUrl + "/test")
                .queryParam("s0", questionList.get(0).getScore())
                .queryParam("s1", questionList.get(1).getScore())
                .queryParam("s2", questionList.get(2).getScore())
                .queryParam("s3", questionList.get(3).getScore())
                .queryParam("s4", questionList.get(4).getScore())
                .queryParam("s5", questionList.get(5).getScore())
                .queryParam("s6", questionList.get(6).getScore())
                .queryParam("s7", questionList.get(7).getScore())
                .queryParam("s8", questionList.get(8).getScore())
                .queryParam("s9", questionList.get(9).getScore())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);

        RestTemplate template = new RestTemplate(factory);

        ResponseEntity<QuestionResultServerResponse> responseEntity = template.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                QuestionResultServerResponse.class);

        if(responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            throw new QuestionServerException();
        }

        QuestionResultServerResponse response = responseEntity.getBody();

        exam.modifyStatus(ExamStatus.FINISHED);
        exam.addTendency(response.getTendency(), response.getTendencyExplanation());
        examRepository.save(exam);

        int tendencyType = 0;

        if (response.getTendency().equals("안전형")) {
            tendencyType = 1;
        } else if (response.getTendency().equals("안전추구형")) {
            tendencyType = 2;
        } else if (response.getTendency().equals("위험중립형")) {
            tendencyType = 3;
        } else if (response.getTendency().equals("적극투자형")) {
            tendencyType = 4;
        } else {
            tendencyType = 5;
        }

        return ExamResult.builder()
                .name(exam.getName())
                .age(exam.getAge())
                .createdAt(exam.getCreatedAt())
                .allQuestionCount(questionList.size())
                .score(scoreSum)
                .questions(questionResponses)
                .tendency(response.getTendency())
                .tendencyExplanation(response.getTendencyExplanation())
                .tendencyType(tendencyType)
                .wrongSum(wrongSum)
                .build();
    }
}
