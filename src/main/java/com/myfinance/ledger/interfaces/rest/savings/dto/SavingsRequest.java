package com.myfinance.ledger.interfaces.rest.savings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class SavingsRequest {

    @NotNull(message = "저축일은 필수입니다")
    @PastOrPresent(message = "저축일은 미래일 수 없습니다")
    private LocalDate savingDate;

    @NotNull(message = "대분류 카테고리는 필수입니다")
    @Positive(message = "카테고리 ID는 양수여야 합니다")
    private Long majorCategoryId;

    @Positive(message = "카테고리 ID는 양수여야 합니다")
    private Long minorCategoryId;

    @Size(max = 20, message = "계좌번호는 20자 이하여야 합니다")
    private String acctNo;

    @Size(max = 30, message = "내용은 30자 이하여야 합니다")
    private String content;

    @NotNull(message = "금액은 필수입니다")
    private BigDecimal amount;
}
