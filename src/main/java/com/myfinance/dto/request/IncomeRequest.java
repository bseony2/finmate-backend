package com.myfinance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class IncomeRequest {

    private Long majorCategoryId;
    private Long minorCategoryId;
    private String content;
    private BigDecimal amount;

    @Builder
    public IncomeRequest(Long majorCategoryId, Long minorCategoryId, String content, BigDecimal amount) {
        this.majorCategoryId = majorCategoryId;
        this.minorCategoryId = minorCategoryId;
        this.content = content;
        this.amount = amount;
    }
}