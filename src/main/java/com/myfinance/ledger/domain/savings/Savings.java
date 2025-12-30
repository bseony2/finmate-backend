package com.myfinance.ledger.domain.savings;

import com.myfinance.ledger.domain.common.BaseEntity;
import com.myfinance.ledger.domain.category.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Savings extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate savingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_category_id", nullable = false)
    private Category majorCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "minor_category_id")
    private Category minorCategory;

    @Column(length = 20)
    private String acctNo;

    @Column(length = 30)
    private String content;

    @Column(nullable = false)
    private BigDecimal amount;

    public static Savings of(LocalDate savingDate, Category majorCategory, Category minorCategory, String acctNo, String content, BigDecimal amount) {

        validateDate(savingDate);
        validateAmount(amount);
        validateCategory(majorCategory);

        Savings savings = new Savings();
        savings.savingDate = savingDate;
        savings.majorCategory = majorCategory;
        savings.minorCategory = minorCategory;
        savings.content = content;
        savings.acctNo = acctNo;
        savings.amount = amount;

        return savings;
    }

    public void update(
            LocalDate savingDate,
            Category majorCategory,
            Category minorCategory,
            String acctNo,
            String content,
            BigDecimal amount)
    {
        validateDate(savingDate);
        validateAmount(amount);
        validateCategory(majorCategory);

        this.savingDate = savingDate;
        this.majorCategory = majorCategory;
        this.minorCategory = minorCategory;
        this.acctNo = acctNo;
        this.content = content;
        this.amount = amount;
    }

    private static void validateCategory(Category majorCategory) {
        if(majorCategory == null) {
            throw new IllegalArgumentException("대분류 카테고리는 null일 수 없습니다.");
        }
    }

    private static void validateAmount(BigDecimal amount) {
        if(amount == null) {
            throw new IllegalArgumentException("금액값은 null일 수 없습니다.");
        }

        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("금액값은 0이하일 수 없습니다.");
        }
    }

    private static void validateDate(LocalDate savingDate) {
        if(savingDate == null) {
            throw new IllegalArgumentException("저축일자는 null일수 없습니다.");
        }
    }
}