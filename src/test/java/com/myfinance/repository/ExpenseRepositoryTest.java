package com.myfinance.repository;

import com.myfinance.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("ExpenseRepository 테스트")
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    private Category majorCategory;
    private Category minorCategory;

    @BeforeEach
    void setUp() {
        CategoryType categoryType = CategoryType.of("가계부");

        // ✅ 실제 프로덕션 메서드 사용
        majorCategory = categoryRepository.save(
                Category.createTopLevelCategory(categoryType, "주거비")
                        .withDisplayOrder(1)
        );

        minorCategory = categoryRepository.save(
                Category.createSubCategory(categoryType, "월세", majorCategory)
                        .withDisplayOrder(1)
        );
    }

    @Test
    @DisplayName("지출을 저장할 수 있다")
    void save_expense() {
        // given
        Expense expense = createExpense(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED
        );

        // when
        Expense saved = expenseRepository.save(expense);

        // then
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("ID로 지출을 조회할 수 있다")
    void find_expense_by_id() {
        // given
        Expense saved = expenseRepository.save(
                createExpense(LocalDate.of(2025, 10, 20), ExpenseType.FIXED)
        );

        // when
        Expense found = expenseRepository.findById(saved.getId()).orElseThrow();

        // then
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getContent()).isEqualTo(saved.getContent());
    }

    @Test
    @DisplayName("특정 기간의 지출을 조회할 수 있다")
    void find_expenses_by_date_range() {
        // given
        LocalDate start = LocalDate.of(2025, 10, 1);
        LocalDate end = LocalDate.of(2025, 10, 31);

        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 15), ExpenseType.FIXED));
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 20), ExpenseType.VARIABLE));
        expenseRepository.save(createExpense(LocalDate.of(2025, 11, 1), ExpenseType.FIXED));

        // when
        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(start, end);

        // then
        assertThat(expenses).hasSize(2);
        assertThat(expenses)
                .allMatch(e -> !e.getExpenseDate().isBefore(start) && !e.getExpenseDate().isAfter(end));
    }

    @Test
    @DisplayName("지출 타입으로 조회할 수 있다")
    void find_expenses_by_type() {
        // given
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 1), ExpenseType.FIXED));
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 5), ExpenseType.FIXED));
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 10), ExpenseType.VARIABLE));

        // when
        List<Expense> fixedExpenses = expenseRepository.findByExpenseType(ExpenseType.FIXED);

        // then
        assertThat(fixedExpenses).hasSize(2);
        assertThat(fixedExpenses).allMatch(Expense::isFixed);
    }

    @Test
    @DisplayName("특정 기간과 타입으로 지출을 조회할 수 있다")
    void find_expenses_by_date_range_and_type() {
        // given
        LocalDate start = LocalDate.of(2025, 10, 1);
        LocalDate end = LocalDate.of(2025, 10, 31);

        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 15), ExpenseType.FIXED));
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 20), ExpenseType.VARIABLE));
        expenseRepository.save(createExpense(LocalDate.of(2025, 11, 1), ExpenseType.FIXED));

        // when
        List<Expense> expenses = expenseRepository.findByExpenseDateBetweenAndExpenseType(
                start, end, ExpenseType.FIXED
        );

        // then
        assertThat(expenses).hasSize(1);
        assertThat(expenses.getFirst().getExpenseType()).isEqualTo(ExpenseType.FIXED);
        assertThat(expenses.getFirst().getExpenseDate()).isBetween(start, end);
    }

    @Test
    @DisplayName("특정 기간의 지출을 날짜 오름차순으로 조회할 수 있다")
    void find_expenses_ordered_by_date() {
        // given
        LocalDate start = LocalDate.of(2025, 10, 1);
        LocalDate end = LocalDate.of(2025, 10, 31);

        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 20), ExpenseType.FIXED));
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 10), ExpenseType.VARIABLE));
        expenseRepository.save(createExpense(LocalDate.of(2025, 10, 15), ExpenseType.FIXED));

        // when
        List<Expense> expenses = expenseRepository
                .findByExpenseDateBetweenOrderByExpenseDateAsc(start, end);

        // then
        assertThat(expenses).hasSize(3);
        assertThat(expenses.get(0).getExpenseDate()).isEqualTo(LocalDate.of(2025, 10, 10));
        assertThat(expenses.get(1).getExpenseDate()).isEqualTo(LocalDate.of(2025, 10, 15));
        assertThat(expenses.get(2).getExpenseDate()).isEqualTo(LocalDate.of(2025, 10, 20));
    }

    @Test
    @DisplayName("지출을 삭제할 수 있다")
    void delete_expense() {
        // given
        Expense saved = expenseRepository.save(
                createExpense(LocalDate.of(2025, 10, 20), ExpenseType.FIXED)
        );

        // when
        expenseRepository.delete(saved);

        // then
        assertThat(expenseRepository.findById(saved.getId())).isEmpty();
    }

    // Helper method
    private Expense createExpense(LocalDate date, ExpenseType type) {
        return Expense.of(
                date,
                type,
                majorCategory,
                minorCategory,
                "테스트 지출",
                new BigDecimal("100000"),
                new BigDecimal("0"),
                new BigDecimal("100000"),
                null
        );
    }
}