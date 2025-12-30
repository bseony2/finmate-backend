package com.myfinance.ledger.interfaces.rest.income.dto;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static IncomeResponse from(Income income) {
        return IncomeResponse.builder()
                .id(income.getId())
                .incomeDate(income.getIncomeDate())
                .majorCategoryId(income.getMajorCategory().getId())
                .majorCategoryName(income.getMajorCategory().getName())
                .minorCategoryId(income.getMinorCategory() != null ? income.getMinorCategory().getId() : null)
                .minorCategoryName(income.getMinorCategory() != null ? income.getMinorCategory().getName() : null)
                .content(income.getContent())
                .amount(income.getAmount())
                .createdAt(income.getCreatedAt())
                .updatedAt(income.getUpdatedAt())
                .build();
    }
}