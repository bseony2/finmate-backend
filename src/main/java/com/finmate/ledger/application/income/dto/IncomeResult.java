package com.finmate.ledger.application.income.dto;

import com.finmate.ledger.domain.income.Income;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class IncomeResult {
    private Long id;
    private LocalDate incomeDate;
    private Long majorCategoryId;
    private String majorCategoryName;
    private Long minorCategoryId;
    private String minorCategoryName;
    private String content;
    private BigDecimal amount;

    public static IncomeResult from(Income income) {
        return new IncomeResult(
                income.getId(),
                income.getIncomeDate(),
                income.getMajorCategory().getId(),
                income.getMajorCategory().getName(),
                income.getMinorCategory().getId(),
                income.getMinorCategory().getName(),
                income.getContent(),
                income.getAmount()
        );
    }
}
