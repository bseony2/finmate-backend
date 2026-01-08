package com.myfinance.ledger.interfaces.rest.income.dto;

import com.myfinance.ledger.application.income.dto.IncomeResult;
import com.myfinance.ledger.domain.income.Income;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class IncomeResponse {

    private Long id;
    private LocalDate incomeDate;
    private Long majorCategoryId;
    private String majorCategoryName;
    private Long minorCategoryId;
    private String minorCategoryName;
    private String content;
    private BigDecimal amount;

    public static IncomeResponse from(IncomeResult result) {
        return IncomeResponse.builder()
                .id(result.getId())
                .incomeDate(result.getIncomeDate())
                .majorCategoryId(result.getMajorCategoryId())
                .majorCategoryName(result.getMajorCategoryName())
                .minorCategoryId(result.getMinorCategoryId() != null ? result.getMinorCategoryId() : null)
                .minorCategoryName(result.getMinorCategoryName() != null ? result.getMinorCategoryName() : null)
                .content(result.getContent())
                .amount(result.getAmount())
                .build();
    }
}