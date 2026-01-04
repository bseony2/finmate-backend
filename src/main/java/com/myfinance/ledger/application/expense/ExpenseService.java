package com.myfinance.ledger.application.expense;

import com.myfinance.ledger.application.category.CategoryRepository;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.expense.Expense;
import com.myfinance.ledger.domain.category.ExpenseType;
import com.myfinance.ledger.interfaces.rest.expense.dto.ExpenseRequest;
import com.myfinance.ledger.interfaces.rest.expense.dto.ExpenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 지출 생성
     */
    @Transactional
    public ExpenseResponse createExpense(ExpenseRequest request) {
        Category majorCategory = categoryRepository.findById(request.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));

        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }

        Expense expense = Expense.of(
                request.getExpenseDate(),
                request.getExpenseType(),
                majorCategory,
                minorCategory,
                request.getContent(),
                request.getPaymentAmount(),
                request.getDiscountAmount(),
                request.getActualAmount(),
                request.getRemark()
        );

        Expense saved = expenseRepository.save(expense);
        return ExpenseResponse.from(saved);
    }

    /**
     * 지출 수정
     */
    @Transactional
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다"));

        Category majorCategory = categoryRepository.findById(request.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));

        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }

        expense.update(
                request.getExpenseDate(),
                majorCategory,
                minorCategory,
                request.getContent(),
                request.getPaymentAmount(),
                request.getDiscountAmount(),
                request.getActualAmount(),
                request.getRemark()
        );

        return ExpenseResponse.from(expense);
    }

    /**
     * 지출 삭제
     */
    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다"));
        expenseRepository.deleteById(expense.getId());
    }

    /**
     * 특정 월의 지출 목록 조회
     */
    public List<ExpenseResponse> getMonthlyExpenses(int year, int month, ExpenseType expenseType) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Expense> expenses;
        if (expenseType != null) {
            expenses = expenseRepository.findByExpenseDateBetweenAndExpenseType(
                    startDate, endDate, expenseType);
        } else {
            expenses = expenseRepository.findByExpenseDateBetweenOrderByExpenseDateAsc(
                    startDate, endDate);
        }

        return expenses.stream()
                .map(ExpenseResponse::from)
                .toList();
    }

    /**
     * 지출 상세 조회
     */
    public ExpenseResponse getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다"));
        return ExpenseResponse.from(expense);
    }
}