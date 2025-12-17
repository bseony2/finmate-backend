package com.myfinance.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class SavingsRequest {

    private Long id;

    private LocalDate savingDate;

    private Long majorCategoryId;

    private Long minorCategoryId;

    private String acctNo;

    private String content;

    private BigDecimal amount;
}
