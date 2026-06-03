package com.finmate.ledger.interfaces.rest.income.dto;

import com.finmate.ledger.application.income.dto.IncomeCommand;
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