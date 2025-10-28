package com.myfinance.service;

import com.myfinance.domain.*;
import com.myfinance.dto.request.ExpenseRequest;
import com.myfinance.dto.response.ExpenseResponse;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.CategoryTypeRepository;
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
@DisplayName("ExpenseService 테스트")
class ExpenseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryTypeRepository categoryTypeRepository;

    private Category majorCategory;
    private Category minorCategory;

    @BeforeEach
    void setUp() {
        CategoryType categoryType = CategoryType.of("가계부");

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
    @DisplayName("지출을 생성할 수 있다")
    void create_expense() {
        // given
        ExpenseRequest request = createRequest(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "월세",
                new BigDecimal("500000")
        );

        // when
        ExpenseResponse response = expenseService.createExpense(request);

        // then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getContent()).isEqualTo("월세");
        assertThat(response.getExpenseType()).isEqualTo(ExpenseType.FIXED);
        assertThat(response.getMajorCategoryName()).isEqualTo("주거비");
        assertThat(response.getMinorCategoryName()).isEqualTo("월세");
        assertThat(response.getPaymentAmount()).isEqualTo(new BigDecimal("500000"));
    }

    @Test
    @DisplayName("소분류 없이 지출을 생성할 수 있다")
    void create_expense_without_minor_category() {
        // given
        ExpenseRequest request = ExpenseRequest.builder()
                .expenseDate(LocalDate.of(2025, 10, 20))
                .expenseType(ExpenseType.FIXED)
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(null)  // 소분류 없음
                .content("기타 주거비")
                .paymentAmount(new BigDecimal("100000"))
                .discountAmount(new BigDecimal("0"))
                .actualAmount(new BigDecimal("100000"))
                .build();

        // when
        ExpenseResponse response = expenseService.createExpense(request);

        // then
        assertThat(response.getMinorCategoryId()).isNull();
        assertThat(response.getMinorCategoryName()).isNull();
        assertThat(response.getMajorCategoryName()).isEqualTo("주거비");
    }

    @Test
    @DisplayName("존재하지 않는 대분류 카테고리로 생성 시 예외가 발생한다")
    void create_expense_with_invalid_major_category() {
        // given
        ExpenseRequest request = ExpenseRequest.builder()
                .expenseDate(LocalDate.of(2025, 10, 20))
                .expenseType(ExpenseType.FIXED)
                .majorCategoryId(999L)
                .minorCategoryId(minorCategory.getId())
                .content("월세")
                .paymentAmount(new BigDecimal("500000"))
                .discountAmount(new BigDecimal("0"))
                .actualAmount(new BigDecimal("500000"))
                .build();

        // when & then
        assertThatThrownBy(() -> expenseService.createExpense(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 대분류 카테고리");
    }

    @Test
    @DisplayName("존재하지 않는 소분류 카테고리로 생성 시 예외가 발생한다")
    void create_expense_with_invalid_minor_category() {
        // given
        ExpenseRequest request = ExpenseRequest.builder()
                .expenseDate(LocalDate.of(2025, 10, 20))
                .expenseType(ExpenseType.FIXED)
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(999L)
                .content("월세")
                .paymentAmount(new BigDecimal("500000"))
                .discountAmount(new BigDecimal("0"))
                .actualAmount(new BigDecimal("500000"))
                .build();

        // when & then
        assertThatThrownBy(() -> expenseService.createExpense(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 소분류 카테고리");
    }

    @Test
    @DisplayName("지출을 수정할 수 있다")
    void update_expense() {
        // given
        ExpenseResponse created = expenseService.createExpense(
                createRequest(
                        LocalDate.of(2025, 10, 20),
                        ExpenseType.FIXED,
                        "월세",
                        new BigDecimal("500000")
                )
        );

        ExpenseRequest updateRequest = ExpenseRequest.builder()
                .expenseDate(LocalDate.of(2025, 10, 21))
                .expenseType(ExpenseType.FIXED)
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(minorCategory.getId())
                .content("월세 수정")
                .paymentAmount(new BigDecimal("550000"))
                .discountAmount(new BigDecimal("10000"))
                .actualAmount(new BigDecimal("540000"))
                .remark("할인받음")
                .build();

        // when
        ExpenseResponse updated = expenseService.updateExpense(created.getId(), updateRequest);

        // then
        assertThat(updated.getId()).isEqualTo(created.getId());
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
        ExpenseRequest request = createRequest(
                LocalDate.of(2025, 10, 20),
                ExpenseType.FIXED,
                "월세",
                new BigDecimal("500000")
        );

        // when & then
        assertThatThrownBy(() -> expenseService.updateExpense(invalidId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출");
    }

    @Test
    @DisplayName("지출을 삭제할 수 있다")
    void delete_expense() {
        // given
        ExpenseResponse created = expenseService.createExpense(
                createRequest(
                        LocalDate.of(2025, 10, 20),
                        ExpenseType.FIXED,
                        "월세",
                        new BigDecimal("500000")
                )
        );

        // when
        expenseService.deleteExpense(created.getId());

        // then
        assertThatThrownBy(() -> expenseService.getExpense(created.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출");
    }

    @Test
    @DisplayName("존재하지 않는 지출 삭제 시 예외가 발생한다")
    void delete_non_existing_expense() {
        // given
        Long invalidId = 999L;

        // when & then
        assertThatThrownBy(() -> expenseService.deleteExpense(invalidId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 지출");
    }

    @Test
    @DisplayName("특정 월의 모든 지출을 조회할 수 있다")
    void get_monthly_expenses() {
        // given
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 15), ExpenseType.VARIABLE, "외식", new BigDecimal("50000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 11, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );

        // when
        List<ExpenseResponse> expenses = expenseService.getMonthlyExpenses(2025, 10, null);

        // then
        assertThat(expenses).hasSize(2);
        assertThat(expenses)
                .extracting(ExpenseResponse::getExpenseDate)
                .allMatch(date -> date.getYear() == 2025 && date.getMonthValue() == 10);
    }

    @Test
    @DisplayName("특정 월의 고정지출만 조회할 수 있다")
    void get_monthly_fixed_expenses() {
        // given
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 15), ExpenseType.VARIABLE, "외식", new BigDecimal("50000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 20), ExpenseType.FIXED, "관리비", new BigDecimal("100000"))
        );

        // when
        List<ExpenseResponse> expenses = expenseService.getMonthlyExpenses(2025, 10, ExpenseType.FIXED);

        // then
        assertThat(expenses).hasSize(2);
        assertThat(expenses).allMatch(e -> e.getExpenseType() == ExpenseType.FIXED);
    }

    @Test
    @DisplayName("특정 월의 변동지출만 조회할 수 있다")
    void get_monthly_variable_expenses() {
        // given
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 1), ExpenseType.FIXED, "월세", new BigDecimal("500000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 15), ExpenseType.VARIABLE, "외식", new BigDecimal("50000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 20), ExpenseType.VARIABLE, "쇼핑", new BigDecimal("30000"))
        );

        // when
        List<ExpenseResponse> expenses = expenseService.getMonthlyExpenses(2025, 10, ExpenseType.VARIABLE);

        // then
        assertThat(expenses).hasSize(2);
        assertThat(expenses).allMatch(e -> e.getExpenseType() == ExpenseType.VARIABLE);
    }

    @Test
    @DisplayName("월별 지출은 날짜 오름차순으로 정렬되어 조회된다")
    void monthly_expenses_are_ordered_by_date() {
        // given
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 20), ExpenseType.FIXED, "C", new BigDecimal("100000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 10), ExpenseType.FIXED, "A", new BigDecimal("100000"))
        );
        expenseService.createExpense(
                createRequest(LocalDate.of(2025, 10, 15), ExpenseType.FIXED, "B", new BigDecimal("100000"))
        );

        // when
        List<ExpenseResponse> expenses = expenseService.getMonthlyExpenses(2025, 10, null);

        // then
        assertThat(expenses).hasSize(3);
        assertThat(expenses.get(0).getContent()).isEqualTo("A");
        assertThat(expenses.get(1).getContent()).isEqualTo("B");
        assertThat(expenses.get(2).getContent()).isEqualTo("C");
    }

    @Test
    @DisplayName("지출 상세를 조회할 수 있다")
    void get_expense_detail() {
        // given
        ExpenseResponse created = expenseService.createExpense(
                createRequest(
                        LocalDate.of(2025, 10, 20),
                        ExpenseType.FIXED,
                        "월세",
                        new BigDecimal("500000")
                )
        );

        // when
        ExpenseResponse found = expenseService.getExpense(created.getId());

        // then
        assertThat(found.getId()).isEqualTo(created.getId());
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
    private ExpenseRequest createRequest(
            LocalDate date,
            ExpenseType type,
            String content,
            BigDecimal paymentAmount
    ) {
        return ExpenseRequest.builder()
                .expenseDate(date)
                .expenseType(type)
                .majorCategoryId(majorCategory.getId())
                .minorCategoryId(minorCategory.getId())
                .content(content)
                .paymentAmount(paymentAmount)
                .discountAmount(new BigDecimal("0"))
                .actualAmount(paymentAmount)
                .build();
    }
}