package com.investment.domain.question.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FinancialServerResponse {

    private int hipr; // 고가
    private int lopr; // 저가
    private int dpr; // 종가
    private int mrktTotAmt; // 시가총액
    private int trPrc; // 거래대금
    private int trqu; // 거래량
    private int vs; // 전일 대비 등락

    public FinancialServerResponse(int hipr, int lopr, int dpr, int mrktTotAmt, int trPrc, int trqu, int vs) {
        this.hipr = hipr;
        this.lopr = lopr;
        this.dpr = dpr;
        this.mrktTotAmt = mrktTotAmt;
        this.trPrc = trPrc;
        this.trqu = trqu;
        this.vs = vs;
    }
}
