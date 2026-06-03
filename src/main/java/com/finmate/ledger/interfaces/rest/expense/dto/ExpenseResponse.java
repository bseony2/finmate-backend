package com.finmate.ledger.interfaces.rest.expense.dto;

import com.finmate.ledger.application.expense.dto.ExpenseResult;
import com.finmate.ledger.domain.category.ExpenseType;
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
    public static ExpenseResponse from(ExpenseResult result) {
        return ExpenseResponse.builder()
                .id(result.getId())
                .expenseDate(result.getExpenseDate())
                .expenseType(result.getExpenseType())
                .majorCategoryId(result.getMajorCategoryId())
                .majorCategoryName(result.getMajorCategoryName())
                .minorCategoryId(result.getMinorCategoryId() != null ?
                        result.getMinorCategoryId() : null)
                .minorCategoryName(result.getMinorCategoryName() != null ?
                        result.getMinorCategoryName() : null)
                .content(result.getContent())
                .paymentAmount(result.getPaymentAmount())
                .discountAmount(result.getDiscountAmount())
                .actualAmount(result.getActualAmount())
                .remark(result.getRemark())
                .build();
    }
}