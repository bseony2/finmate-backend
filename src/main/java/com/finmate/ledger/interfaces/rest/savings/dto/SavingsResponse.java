package com.finmate.ledger.interfaces.rest.savings.dto;

import com.finmate.ledger.application.savings.dto.SavingsResult;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 저축 응답 DTO
 */
@Getter
@Builder
public class SavingsResponse {

    // 저축 ID
    private Long id;

    // 저축일
    private LocalDate savingDate;

    // 대분류 카테고리 ID
    private Long majorCategoryId;

    // 대분류 카테고리명
    private String majorCategoryName;

    // 소분류 카테고리 ID
    private Long minorCategoryId;

    // 소분류 카테고리명
    private String minorCategoryName;

    // 계좌번호
    private String acctNo;

    // 내용
    private String content;

    // 금액
    private BigDecimal amount;

    /**
     * Entity → Response DTO 변환
     *
     * @param savings 저축 엔티티
     * @return SavingsResponse
     */
    public static SavingsResponse from(SavingsResult savings) {
        return SavingsResponse.builder()
                .id(savings.getId())
                .savingDate(savings.getSavingDate())
                .majorCategoryId(savings.getMajorCategoryId())
                .majorCategoryName(savings.getMajorCategoryName())
                .minorCategoryId(savings.getMinorCategoryId() != null ?
                        savings.getMinorCategoryId() : null)
                .minorCategoryName(savings.getMinorCategoryName() != null ?
                        savings.getMinorCategoryName() : null)
                .acctNo(savings.getAcctNo())
                .content(savings.getContent())
                .amount(savings.getAmount())
                .build();
    }
}