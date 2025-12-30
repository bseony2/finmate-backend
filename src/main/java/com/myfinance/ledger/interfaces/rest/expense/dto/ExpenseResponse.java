package com.myfinance.ledger.interfaces.rest.expense.dto;

import com.myfinance.ledger.domain.expense.Expense;
import com.myfinance.ledger.domain.category.ExpenseType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 지출 응답 DTO
 */
@Getter
@Builder
public class ExpenseResponse {

    private Long id;
    private LocalDate expenseDate;
    private ExpenseType expenseType;  // 프론트로 0 또는 1 전송
    private Long majorCategoryId;
    private String majorCategoryName;
    private Long minorCategoryId;
    private String minorCategoryName;
    private String content;
    private BigDecimal paymentAmount;
    private BigDecimal discountAmount;
    private BigDecimal actualAmount;
    private String remark;

    /**
     * Entity → DTO 변환
     */
    public static ExpenseResponse from(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .expenseDate(expense.getExpenseDate())
                .expenseType(expense.getExpenseType())
                .majorCategoryId(expense.getMajorCategory().getId())
                .majorCategoryName(expense.getMajorCategory().getName())
                .minorCategoryId(expense.getMinorCategory() != null ?
                        expense.getMinorCategory().getId() : null)
                .minorCategoryName(expense.getMinorCategory() != null ?
                        expense.getMinorCategory().getName() : null)
                .content(expense.getContent())
                .paymentAmount(expense.getPaymentAmount())
                .discountAmount(expense.getDiscountAmount())
                .actualAmount(expense.getActualAmount())
                .remark(expense.getRemark())
                .build();
    }
}