package com.myfinance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Income extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 대분류 카테고리
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id", nullable = false)
    private Category majorCategory;

    /**
     * 소분류 카테고리
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_category_id")
    private Category minorCategory;

    /**
     * 내용
     */
    @Column(name = "CONTENT", length = 30)
    private String content;

    /**
     * 금액
     */
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    public Income of(
            Category majorCategory
            , Category minorCategory
            , String content
            , BigDecimal amount
    ) {
        Income income = new Income();
        income.majorCategory = majorCategory;
        income.minorCategory = minorCategory;
        income.content = content;
        income.amount = amount;

        return income;
    }
}
