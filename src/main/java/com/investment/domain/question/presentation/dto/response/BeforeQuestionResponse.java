package com.investment.domain.question.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.investment.domain.finance.domain.entity.FinanceInfo;
import com.investment.domain.news.domain.entity.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@AllArgsConstructor
public class BeforeQuestionResponse {

    private long id;
    private String image;
    private News news;
    private FinanceInfo financeInfo;
    private String stock;
}
