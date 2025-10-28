package com.myfinance.service;

import com.myfinance.domain.Category;
import com.myfinance.domain.Expense;
import com.myfinance.domain.ExpenseType;
import com.myfinance.dto.request.ExpenseRequest;
import com.myfinance.dto.response.ExpenseResponse;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.ExpenseRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리"));

        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리"));
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출"));

        Category majorCategory = categoryRepository.findById(request.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리"));

        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리"));
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출"));
        expenseRepository.delete(expense);
    }

    /**
     * 특정 월의 지출 목록 조회
     */
    public List<ExpenseResponse> getMonthlyExpenses(int year, int month, ExpenseType expenseType) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출"));
        return ExpenseResponse.from(expense);
    }
}