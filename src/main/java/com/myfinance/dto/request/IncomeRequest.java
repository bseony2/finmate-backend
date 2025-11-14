package com.myfinance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class IncomeRequest {

    private LocalDate incomeDate;
    private Long majorCategoryId;
    private Long minorCategoryId;
    private String content;
    private BigDecimal amount;

    @Builder
    public IncomeRequest(LocalDate incomeDate, Long majorCategoryId, Long minorCategoryId, String content, BigDecimal amount) {
        this.incomeDate = incomeDate;
        this.majorCategoryId = majorCategoryId;
        this.minorCategoryId = minorCategoryId;
        this.content = content;
        this.amount = amount;
    }
}