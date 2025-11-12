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

    public static Income of(
            Category majorCategory
            , Category minorCategory
            , String content
            , BigDecimal amount
    ) {
        if (amount == null) {
            throw new IllegalArgumentException("금액은 null일수 없습니다");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("금액은 0보다 작거나 같을수 없습니다");
        }
        Income income = new Income();
        income.majorCategory = majorCategory;
        income.minorCategory = minorCategory;
        income.content = content;
        income.amount = amount;

        return income;
    }

    public void update(
            Category majorCategory,
            Category minorCategory,
            String content,
            BigDecimal amount
    ) {
        if (amount == null) {
            throw new IllegalArgumentException("금액은 null일수 없습니다");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("금액은 0보다 작거나 같을수 없습니다");
        }

        this.majorCategory = majorCategory;
        this.minorCategory = minorCategory;
        this.content = content;
        this.amount = amount;
    }
}
