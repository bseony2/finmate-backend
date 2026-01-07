package com.myfinance.ledger.application.expense.dto;

import com.myfinance.ledger.domain.category.ExpenseType;
import com.myfinance.ledger.domain.expense.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseResult {

    private Long id;
    private LocalDate expenseDate;
    private ExpenseType expenseType;
    private Long majorCategoryId;
    private String majorCategoryName;
    private Long minorCategoryId;
    private String minorCategoryName;
    private String content;
    private BigDecimal paymentAmount;
    private BigDecimal discountAmount;
    private BigDecimal actualAmount;
    private String remark;

    public static ExpenseResult from(Expense expense) {
        return new ExpenseResult(
                expense.getId(),
                expense.getExpenseDate(),
                expense.getExpenseType(),
                expense.getMajorCategory().getId(),
                expense.getMajorCategory().getName(),
                expense.getMinorCategory() != null ?
                        expense.getId() : null,
                expense.getMinorCategory() != null ?
                        expense.getMinorCategory().getName() : null,
                expense.getContent(),
                expense.getPaymentAmount(),
                expense.getDiscountAmount(),
                expense.getActualAmount(),
                expense.getRemark()
        );
    }
}
