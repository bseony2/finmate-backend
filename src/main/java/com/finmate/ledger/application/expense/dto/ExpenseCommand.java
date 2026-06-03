package com.finmate.ledger.application.expense.dto;

import com.finmate.ledger.domain.category.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseCommand {
    private LocalDate expenseDate;
    private ExpenseType expenseType;
    private Long majorCategoryId;
    private Long minorCategoryId;
    private String content;
    private BigDecimal paymentAmount;
    private BigDecimal discountAmount;
    private BigDecimal actualAmount;
    private String remark;
}
