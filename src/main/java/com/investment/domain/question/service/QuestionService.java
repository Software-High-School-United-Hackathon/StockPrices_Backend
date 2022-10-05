package com.investment.domain.question.service;

import com.investment.domain.exam.domain.entity.Exam;
import com.investment.domain.exam.domain.repository.ExamRepository;
import com.investment.domain.exam.exception.ExamNotFoundException;
import com.investment.domain.question.domain.entity.Question;
import com.investment.domain.question.domain.repository.QuestionRepository;
import com.investment.domain.question.exception.QuestionNotFoundException;
import com.investment.domain.question.exception.QuestionServerException;
import com.investment.domain.question.presentation.dto.request.InsertAnswerRequest;
import com.investment.domain.question.presentation.dto.response.BeforeQuestionResponse;
import com.investment.domain.question.presentation.dto.response.QuestionClientResponse;
import com.investment.domain.upload.service.UploadService;
import com.investment.global.libs.MultipartParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final UploadService uploadService;
    private final MultipartParser multipartParser;

    @Value("${api.question}")
    private String questionApiUrl;

    @Transactional(rollbackFor = Exception.class)
    public BeforeQuestionResponse getQuestion(String id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(ExamNotFoundException::new);

        WebClient webClient = WebClient.builder()
                .baseUrl(questionApiUrl)
                .build();

        QuestionClientResponse response = webClient.get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(QuestionClientResponse.class)
                .block();

        if (response == null) {
            throw new QuestionServerException();
        }

        MultipartFile file = multipartParser.changeBase64ToMultipartFile(response.getImage());
        String uploadedImageUrl = uploadService.uploadFile(file);

        Question question = Question.builder()
                .answer(response.getAnswer())
                .explanation(response.getExplanation())
                .exam(exam)
                .image(uploadedImageUrl)
                .uniqueCode(response.getCode())
                .build();

        return BeforeQuestionResponse.builder()
                .id(question.getId())
                .image(question.getImage())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertAnswer(InsertAnswerRequest request) {

        Question question = questionRepository.findById(request.getId())
                        .orElseThrow(QuestionNotFoundException::new);

        question.updateAnswer(request.getAnswer());

        questionRepository.save(question);
    }
}
