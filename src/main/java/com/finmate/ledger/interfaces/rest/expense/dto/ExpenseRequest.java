package com.finmate.ledger.interfaces.rest.expense.dto;

import com.finmate.ledger.application.expense.dto.ExpenseCommand;
import com.finmate.ledger.domain.category.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 지출 생성/수정 요청 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    private LocalDate expenseDate;

    private ExpenseType expenseType;  // 프론트에서 0 또는 1

    private Long majorCategoryId;

    private Long minorCategoryId;

    private String content;

    private BigDecimal paymentAmount;

    private BigDecimal discountAmount;

    private BigDecimal actualAmount;

    private String remark;

    public ExpenseCommand toCommand() {
        return new ExpenseCommand(
                this.expenseDate,
                this.expenseType,
                this.majorCategoryId,
                this.minorCategoryId,
                this.content,
                this.paymentAmount,
                this.discountAmount,
                this.actualAmount,
                this.remark
        );
    }
}