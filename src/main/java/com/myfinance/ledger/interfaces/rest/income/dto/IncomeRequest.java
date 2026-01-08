package com.myfinance.ledger.interfaces.rest.income.dto;

import com.myfinance.ledger.application.income.dto.IncomeCommand;
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

    public IncomeCommand toCommand() {
        return new IncomeCommand(
                this.incomeDate,
                this.majorCategoryId,
                this.minorCategoryId,
                this.content,
                this.amount
        );
    }
}