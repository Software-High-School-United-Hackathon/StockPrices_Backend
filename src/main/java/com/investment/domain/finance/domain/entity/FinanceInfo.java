package com.investment.domain.finance.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "finance_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinanceInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int hipr; // 고가

    @Column(nullable = false)
    private int lopr; // 저가

    @Column(nullable = false)
    private int dpr; // 종가

    @Column(nullable = false)
    private int mrktTotAmt; // 시가총액

    @Column(nullable = false)
    private int trPrc; // 거래대금

    @Column(nullable = false)
    private int trqu; // 거래량

    @Column(nullable = false)
    private int vs; // 전일 대비 등락

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Builder
    public FinanceInfo(int hipr, int lopr, int dpr, int mrktTotAmt, int trPrc, int trqu, int vs, LocalDate endDate) {
        this.hipr = hipr;
        this.lopr = lopr;
        this.dpr = dpr;
        this.mrktTotAmt = mrktTotAmt;
        this.trPrc = trPrc;
        this.trqu = trqu;
        this.vs = vs;
        this.endDate = endDate;
    }
}
