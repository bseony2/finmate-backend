package com.finmate.ledger.application.savings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SavingsCommand {
    private LocalDate savingDate;
    private Long majorCategoryId;
    private Long minorCategoryId;
    private String acctNo;
    private String content;
    private BigDecimal amount;

}
