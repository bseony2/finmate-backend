package com.myfinance.ledger.application.income.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class IncomeCommand {
    private LocalDate incomeDate;
    private Long majorCategoryId;
    private Long minorCategoryId;
    private String content;
    private BigDecimal amount;
}
