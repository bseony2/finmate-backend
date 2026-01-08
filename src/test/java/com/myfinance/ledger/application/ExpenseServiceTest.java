package com.myfinance.ledger.application;

import com.myfinance.ledger.application.expense.ExpenseService;
import com.myfinance.ledger.application.expense.dto.ExpenseCommand;
import com.myfinance.ledger.application.expense.dto.ExpenseResult;
import com.myfinance.ledger.domain.category.ExpenseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("ExpenseService 테스트")
class ExpenseServiceTest extends AbstractServiceTest{

    @Autowired
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {

        String type = "test";
        String major = "주거비";
        String minor = "월세";

        setInitCategory(type, major, minor);
    }

    @Test
    @DisplayName("지출을 생성할 수 있다")
    void create_expense() {
        // given
        ExpenseCommand command = createCommand(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "월세",
                new BigDecimal("500000")
        );

        // when
        ExpenseResult result = expenseService.createExpense(command);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("월세");
        assertThat(result.getExpenseType()).isEqualTo(ExpenseType.FIXED);
        assertThat(result.getMajorCategoryName()).isEqualTo("주거비");
        assertThat(result.getMinorCategoryName()).isEqualTo("월세");
        assertThat(result.getPaymentAmount()).isEqualTo(new BigDecimal("500000"));
    }

    @Test
    @DisplayName("소분류 없이 지출을 생성할 수 있다")
    void create_expense_without_minor_category() {
        // given
        ExpenseCommand command = createCommandWithCategoryId(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "기타 주거비",
                new BigDecimal("100000"),
                majorCategory.getId(),
                null
        );

        // when
        ExpenseResult result = expenseService.createExpense(command);

        // then
        assertThat(result.getMinorCategoryId()).isNull();
        assertThat(result.getMinorCategoryName()).isNull();
        assertThat(result.getMajorCategoryName()).isEqualTo("주거비");
    }

    @Test
    @DisplayName("존재하지 않는 대분류 카테고리로 생성 시 예외가 발생한다")
    void create_expense_with_invalid_major_category() {
        // given
        ExpenseCommand command = createCommandWithCategoryId(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "없는대분류",
                new BigDecimal("500000"),
                -1L,
                -1L
        );
        // when & then
        assertThatThrownBy(() -> expenseService.createExpense(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 대분류 카테고리");
    }

    @Test
    @DisplayName("존재하지 않는 소분류 카테고리로 생성 시 예외가 발생한다")
    void create_expense_with_invalid_minor_category() {
        // given
        ExpenseCommand command = createCommandWithCategoryId(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "월세",
                new BigDecimal("500000"),
                majorCategory.getId(),
                -1L
        ) ;
        // when & then
        assertThatThrownBy(() -> expenseService.createExpense(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 소분류 카테고리");
    }

    @Test
    @DisplayName("지출을 수정할 수 있다")
    void update_expense() {
        // given
        ExpenseResult result = expenseService.createExpense(
                createCommand(
                        LocalDate.of(2025, 10, 20),
                        ExpenseType.FIXED,
                        "월세",
                        new BigDecimal("500000")
                )
        );

        ExpenseCommand command = createCommand(
                LocalDate.of(2025, 10, 21),
                ExpenseType.FIXED,
                majorCategory.getId(),
                minorCategory.getId(),
                "월세 수정",
                new BigDecimal("550000"),
                new BigDecimal("10000"),
                new BigDecimal("540000"),
                "할인받음"
        );

        // when
        ExpenseResult updated = expenseService.updateExpense(result.getId(), command);

        // then
        assertThat(updated.getId()).isEqualTo(result.getId());
        assertThat(updated.getExpenseDate()).isEqualTo(LocalDate.of(2025, 10, 21));
        assertThat(updated.getContent()).isEqualTo("월세 수정");
        assertThat(updated.getPaymentAmount()).isEqualTo(new BigDecimal("550000"));
        assertThat(updated.getDiscountAmount()).isEqualTo(new BigDecimal("10000"));
        assertThat(updated.getActualAmount()).isEqualTo(new BigDecimal("540000"));
        assertThat(updated.getRemark()).isEqualTo("할인받음");
    }

    @Test
    @DisplayName("존재하지 않는 지출 수정 시 예외가 발생한다")
    void update_non_existing_expense() {
        // given
        Long invalidId = 999L;
        ExpenseCommand command = createCommand(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "월세",
                new BigDecimal("500000")
        );

        // when & then
        assertThatThrownBy(() -> expenseService.updateExpense(invalidId, command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출");
    }

    @Test
    @DisplayName("지출을 삭제할 수 있다")
    void delete_expense() {
        // given
        ExpenseResult result = expenseService.createExpense(
                createCommand(
                        LocalDate.of(2025, 10, 20),
                        ExpenseType.FIXED,
                        "월세",
                        new BigDecimal("500000")
                )
        );

        Long id = result.getId();

        // when
        expenseService.deleteExpense(id);

        // then
        assertThatThrownBy(() -> expenseService.getExpense(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출입니다");
    }

    @Test
    @DisplayName("존재하지 않는 지출 삭제 시 예외가 발생한다")
    void delete_non_existing_expense() {
        // given
        Long invalidId = -1L;

        // when & then
        assertThatThrownBy(() -> expenseService.deleteExpense(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출입니다");
    }

    @Test
    @DisplayName("특정 월의 모든 지출을 조회할 수 있다")
    void get_monthly_expenses() {
        // given
        expenseService.createExpense(
                createCommand(LocalDate.of(2025, 10, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(2025, 10, 15), ExpenseType.VARIABLE, "외식", new BigDecimal("50000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(2025, 11, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );

        // when
        List<ExpenseResult> results = expenseService.getMonthlyExpenses(2025, 10, null);

        // then
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(ExpenseResult::getExpenseDate)
                .allMatch(date -> date.getYear() == 2025 && date.getMonthValue() == 10);
    }

    @Test
    @DisplayName("특정 월의 고정지출만 조회할 수 있다")
    void get_monthly_fixed_expenses() {
        // given
        expenseService.createExpense(
                createCommand(LocalDate.of(1, 10, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(1, 10, 15), ExpenseType.VARIABLE, "외식", new BigDecimal("50000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(1, 10, 20), ExpenseType.FIXED, "관리비", new BigDecimal("100000"))
        );

        // when
        List<ExpenseResult> results = expenseService.getMonthlyExpenses(1, 10, ExpenseType.FIXED);

        // then
        assertThat(results)
                .hasSize(2)
                .allMatch(e -> e.getExpenseType() == ExpenseType.FIXED);
    }

    @Test
    @DisplayName("특정 월의 변동지출만 조회할 수 있다")
    void get_monthly_variable_expenses() {
        // given
        expenseService.createExpense(
                createCommand(LocalDate.of(2025, 10, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(2025, 10, 15), ExpenseType.VARIABLE, "외식", new BigDecimal("50000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(2025, 10, 20), ExpenseType.VARIABLE, "쇼핑", new BigDecimal("30000"))
        );

        // when
        List<ExpenseResult> results = expenseService.getMonthlyExpenses(2025, 10, ExpenseType.VARIABLE);

        // then
        assertThat(results)
                .hasSize(2)
                .allMatch(e -> e.getExpenseType() == ExpenseType.VARIABLE);
    }

    @Test
    @DisplayName("월별 지출은 날짜 오름차순으로 정렬되어 조회된다")
    void monthly_expenses_are_ordered_by_date() {
        // given
        expenseService.createExpense(
                createCommand(LocalDate.of(1, 10, 20), ExpenseType.FIXED, "C", new BigDecimal("100000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(1, 10, 10), ExpenseType.FIXED, "A", new BigDecimal("100000"))
        );
        expenseService.createExpense(
                createCommand(LocalDate.of(1, 10, 15), ExpenseType.FIXED, "B", new BigDecimal("100000"))
        );

        // when
        List<ExpenseResult> results = expenseService.getMonthlyExpenses(1, 10, null);

        // then
        assertThat(results).hasSize(3);
        assertThat(results.get(0).getContent()).isEqualTo("A");
        assertThat(results.get(1).getContent()).isEqualTo("B");
        assertThat(results.get(2).getContent()).isEqualTo("C");
    }

    @Test
    @DisplayName("지출 상세를 조회할 수 있다")
    void get_expense_detail() {
        // given
        ExpenseResult result = expenseService.createExpense(
                createCommand(
                        LocalDate.of(2025, 10, 20),
                        ExpenseType.FIXED,
                        "월세",
                        new BigDecimal("500000")
                )
        );

        // when
        ExpenseResult found = expenseService.getExpense(result.getId());

        // then
        assertThat(found.getId()).isEqualTo(result.getId());
        assertThat(found.getContent()).isEqualTo("월세");
        assertThat(found.getPaymentAmount()).isEqualTo(new BigDecimal("500000"));
    }

    @Test
    @DisplayName("존재하지 않는 지출 조회 시 예외가 발생한다")
    void get_non_existing_expense() {
        // given
        Long invalidId = 999L;

        // when & then
        assertThatThrownBy(() -> expenseService.getExpense(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출");
    }

    // Helper method
    private ExpenseCommand createCommand(
            LocalDate date,
            ExpenseType type,
            String content,
            BigDecimal paymentAmount
    ) {
        return new ExpenseCommand(
                date,
                type,
                majorCategory.getId(),
                minorCategory.getId(),
                content,
                paymentAmount,
                new BigDecimal("0"),
                paymentAmount,
                ""
        );
    }

    private ExpenseCommand createCommandWithCategoryId(
            LocalDate date,
            ExpenseType type,
            String content,
            BigDecimal paymentAmount,
            Long majorCategoryId,
            Long minorCategoryId
    ) {
        return new ExpenseCommand(
                date,
                type,
                majorCategoryId,
                minorCategoryId,
                content,
                paymentAmount,
                new BigDecimal("0"),
                paymentAmount,
                ""
        );
    }

    private ExpenseCommand createCommand(
            LocalDate date,
            ExpenseType type,
            Long majorCategoryId,
            Long minorCategoryId,
            String content,
            BigDecimal paymentAmount,
            BigDecimal discountAmount,
            BigDecimal actualAmount,
            String remark
    ){

        return new ExpenseCommand(date, type, majorCategoryId, minorCategoryId, content, paymentAmount, discountAmount, actualAmount, remark);

    }
}