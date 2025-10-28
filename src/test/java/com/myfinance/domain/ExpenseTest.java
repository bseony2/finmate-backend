package com.myfinance.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Expense 엔티티 테스트")
class ExpenseTest {

    private Category majorCategory;
    private Category minorCategory;
    private CategoryType categoryType;

    @BeforeEach
    void setUp() {
        // ID 없이 생성 (null)
        categoryType = CategoryType.of("가계부");
        majorCategory = Category.createTopLevelCategory(categoryType, "주거비");
        minorCategory = Category.createSubCategory(categoryType, "월세", majorCategory);
    }

    @Test
    @DisplayName("Expense를 생성할 수 있다")
    void create_expense() {
        // given
        LocalDate date = LocalDate.of(2025, 10, 20);
        ExpenseType type = ExpenseType.FIXED;
        String content = "월세";
        BigDecimal payment = new BigDecimal("500000");
        BigDecimal discount = new BigDecimal("0");
        BigDecimal actual = new BigDecimal("500000");

        // when
        Expense expense = Expense.of(
                date, type,
                majorCategory, minorCategory,
                content,
                payment, discount, actual,
                null
        );

        // then
        assertThat(expense.getExpenseDate()).isEqualTo(date);
        assertThat(expense.getExpenseType()).isEqualTo(type);
        assertThat(expense.getMajorCategory()).isEqualTo(majorCategory);
        assertThat(expense.getMinorCategory()).isEqualTo(minorCategory);
        assertThat(expense.getContent()).isEqualTo(content);
        assertThat(expense.getPaymentAmount()).isEqualTo(payment);
        assertThat(expense.getDiscountAmount()).isEqualTo(discount);
        assertThat(expense.getActualAmount()).isEqualTo(actual);
    }

    @Test
    @DisplayName("소분류 카테고리 없이 Expense를 생성할 수 있다")
    void create_expense_without_minor_category() {
        // when
        Expense expense = Expense.of(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                majorCategory,
                null,  // 소분류 없음
                "월세",
                new BigDecimal("500000"),
                new BigDecimal("0"),
                new BigDecimal("500000"),
                null
        );

        // then
        assertThat(expense.getMinorCategory()).isNull();
        assertThat(expense.getMajorCategory()).isEqualTo(majorCategory);
    }

    @Test
    @DisplayName("비고와 함께 Expense를 생성할 수 있다")
    void create_expense_with_remark() {
        // given
        String remark = "할인 받음";

        // when
        Expense expense = Expense.of(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                majorCategory, minorCategory,
                "월세",
                new BigDecimal("500000"),
                new BigDecimal("50000"),
                new BigDecimal("450000"),
                remark
        );

        // then
        assertThat(expense.getRemark()).isEqualTo(remark);
    }

    @Test
    @DisplayName("Expense를 수정할 수 있다")
    void update_expense() {
        // given
        Expense expense = Expense.of(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                majorCategory, minorCategory,
                "월세",
                new BigDecimal("500000"),
                new BigDecimal("0"),
                new BigDecimal("500000"),
                null
        );

        LocalDate newDate = LocalDate.of(2025, 10, 21);
        String newContent = "월세 수정";
        BigDecimal newPayment = new BigDecimal("550000");
        BigDecimal newDiscount = new BigDecimal("10000");
        BigDecimal newActual = new BigDecimal("540000");
        String newRemark = "할인받음";

        // when
        expense.update(
                newDate,
                majorCategory, minorCategory,
                newContent,
                newPayment, newDiscount, newActual,
                newRemark
        );

        // then
        assertThat(expense.getExpenseDate()).isEqualTo(newDate);
        assertThat(expense.getContent()).isEqualTo(newContent);
        assertThat(expense.getPaymentAmount()).isEqualTo(newPayment);
        assertThat(expense.getDiscountAmount()).isEqualTo(newDiscount);
        assertThat(expense.getActualAmount()).isEqualTo(newActual);
        assertThat(expense.getRemark()).isEqualTo(newRemark);
    }

    @Test
    @DisplayName("고정지출 여부를 확인할 수 있다")
    void check_if_fixed_expense() {
        // given
        Expense fixedExpense = createExpense(ExpenseType.FIXED);
        Expense variableExpense = createExpense(ExpenseType.VARIABLE);

        // when & then
        assertThat(fixedExpense.isFixed()).isTrue();
        assertThat(fixedExpense.isVariable()).isFalse();
        assertThat(variableExpense.isFixed()).isFalse();
        assertThat(variableExpense.isVariable()).isTrue();
    }

    @Test
    @DisplayName("변동지출 여부를 확인할 수 있다")
    void check_if_variable_expense() {
        // given
        Expense variableExpense = createExpense(ExpenseType.VARIABLE);

        // when & then
        assertThat(variableExpense.isVariable()).isTrue();
        assertThat(variableExpense.isFixed()).isFalse();
    }

    // Helper methods
    private Expense createExpense(ExpenseType type) {
        return Expense.of(
                LocalDate.of(2025, 10, 20),
                type,
                majorCategory, minorCategory,
                "테스트",
                new BigDecimal("100000"),
                new BigDecimal("0"),
                new BigDecimal("100000"),
                null
        );
    }
}