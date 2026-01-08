package com.myfinance.ledger.application.savings.dto;

import com.myfinance.ledger.domain.savings.Savings;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SavingsResult {
    private Long id;
    private LocalDate savingDate;
    private Long majorCategoryId;
    private String majorCategoryName;
    private Long minorCategoryId;
    private String minorCategoryName;
    private String acctNo;
    private String content;
    private BigDecimal amount;

    public static SavingsResult from(Savings savings) {
        return new SavingsResult(
                savings.getId(),
                savings.getSavingDate(),
                savings.getMajorCategory().getId(),
                savings.getMajorCategory().getName(),
                savings.getMinorCategory().getId(),
                savings.getMinorCategory().getName(),
                savings.getAcctNo(),
                savings.getContent(),
                savings.getAmount()
        );
    }
}
