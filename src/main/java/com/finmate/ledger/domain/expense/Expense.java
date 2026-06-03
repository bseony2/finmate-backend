package com.finmate.ledger.domain.expense;

import com.finmate.ledger.domain.common.BaseEntity;
import com.finmate.ledger.domain.category.Category;
import com.finmate.ledger.domain.category.ExpenseType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 지출 엔티티 (고정지출 + 변동지출)
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 지출일자
     */
    @Column(nullable = false)
    private LocalDate expenseDate;

    /**
     * 지출 구분 (0: 고정지출, 1: 변동지출)
     */
    @Convert(converter = ExpenseTypeConverter.class)
    @Column(nullable = false)
    private ExpenseType expenseType;

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
     * 지출내용
     */
    @Column(length = 30)
    private String content;

    /**
     * 결재금액
     */
    @Column(nullable = false)
    private BigDecimal paymentAmount;

    /**
     * 할인금액
     */
    @Column()
    private BigDecimal discountAmount;

    /**
     * 실지출금액 (결재금액 - 할인금액)
     */
    @Column()
    private BigDecimal actualAmount;

    /**
     * 비고
     */
    @Column(length = 50)
    private String remark;

    /**
     * 정적 팩터리 메서드 - 지출 생성
     */
    public static Expense of(
            LocalDate expenseDate,
            ExpenseType expenseType,
            Category majorCategory,
            Category minorCategory,
            String content,
            BigDecimal paymentAmount,
            BigDecimal discountAmount,
            BigDecimal actualAmount,
            String remark
    ) {
        Expense expense = new Expense();
        expense.expenseDate = expenseDate;
        expense.expenseType = expenseType;
        expense.majorCategory = majorCategory;
        expense.minorCategory = minorCategory;
        expense.content = content;
        expense.paymentAmount = paymentAmount;
        expense.discountAmount = discountAmount;
        expense.actualAmount = actualAmount;  // 자동 계산
        expense.remark = remark;

        return expense;
    }

    /**
     * 지출 정보 수정
     */
    public void update(
            LocalDate expenseDate,
            Category majorCategory,
            Category minorCategory,
            String content,
            BigDecimal paymentAmount,
            BigDecimal discountAmount,
            BigDecimal actualAmount,
            String remark
    ) {
        this.expenseDate = expenseDate;
        this.majorCategory = majorCategory;
        this.minorCategory = minorCategory;
        this.content = content;
        this.paymentAmount = paymentAmount;
        this.discountAmount = discountAmount;
        this.actualAmount = actualAmount;  // 자동 재계산
        this.remark = remark;
    }

    public boolean isFixed() {
        return expenseType == ExpenseType.FIXED;
    }

    public boolean isVariable() {
        return expenseType == ExpenseType.VARIABLE;
    }
}