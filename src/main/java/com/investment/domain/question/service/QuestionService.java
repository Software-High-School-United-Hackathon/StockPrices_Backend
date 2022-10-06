package com.investment.domain.question.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.exception.ExamNotFoundException;
import com.investment.domain.news.domain.entity.News;
import com.investment.domain.news.domain.repository.NewsRepository;
import com.investment.domain.question.domain.entity.Question;
import com.investment.domain.question.domain.repository.QuestionRepository;
import com.investment.domain.question.exception.QuestionNotFoundException;
import com.investment.domain.question.exception.QuestionServerException;
import com.investment.domain.question.presentation.dto.request.InsertAnswerRequest;
import com.investment.domain.question.presentation.dto.response.BeforeQuestionResponse;
import com.investment.domain.question.presentation.dto.response.QuestionResponse;
import com.investment.domain.question.presentation.dto.response.QuestionServerResponse;
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
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final NewsRepository newsRepository;

    @Value("${api.question}")
    private String questionApiBaseUrl;

    @Transactional(rollbackFor = Exception.class)
    public BeforeQuestionResponse getQuestion(String id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(ExamNotFoundException::new);

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(questionApiBaseUrl).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(10000);

        RestTemplate template = new RestTemplate(factory);

        ResponseEntity<QuestionServerResponse> responseEntity = template.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                QuestionServerResponse.class);

        if(responseEntity.getStatusCode().is4xxClientError() || responseEntity.getStatusCode().is5xxServerError()) {
            throw new QuestionServerException();
        }

        QuestionServerResponse response = responseEntity.getBody();

        News createdNews = News.builder()
                .title(response.getNewsTitle())
                .article(response.getNewsArticle())
                .image(response.getNewsImgUrl())
                .build();
        newsRepository.save(createdNews);

        Question createdQuestion = Question.builder()
                .answer(response.getAnswer())
                .explanation(response.getExplanation())
                .exam(exam)
                .image(response.getImage())
                .uniqueCode(response.getCode())
                .news(createdNews)
                .build();
        questionRepository.save(createdQuestion);

        return BeforeQuestionResponse.builder()
                .id(createdQuestion.getId())
                .image(createdQuestion.getImage())
                .news(createdNews)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertAnswer(InsertAnswerRequest request) {

        Question question = questionRepository.findById(request.getId())
                        .orElseThrow(QuestionNotFoundException::new);

        question.updateAnswerAndScore(request.getAnswer());

        questionRepository.save(question);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public QuestionResponse getQuestionById(long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(QuestionNotFoundException::new);
        return QuestionResponse.builder()
                .id(question.getId())
                .score(question.getScore())
                .explanation(question.getExplanation())
                .answer(question.getAnswer())
                .rightAnswer(question.getRightAnswer())
                .news(question.getNews())
                .build();
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<QuestionResponse> getWrongQuestions(String examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(ExamNotFoundException::new);

        List<Question> questionList = questionRepository.findAllByExam(exam);
        List<QuestionResponse> wrongQuestionList = new ArrayList<>();

        for (Question question : questionList) {
            if (question.getScore() < 5) {
                wrongQuestionList.add(
                        QuestionResponse.builder()
                                .id(question.getId())
                                .score(question.getScore())
                                .news(question.getNews())
                                .explanation(question.getExplanation())
                                .answer(question.getAnswer())
                                .rightAnswer(question.getRightAnswer())
                                .build()
                );
            }
        }

        return wrongQuestionList;
    }
}
