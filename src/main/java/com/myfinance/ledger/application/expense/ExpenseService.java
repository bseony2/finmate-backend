package com.myfinance.ledger.application.expense;

import com.myfinance.ledger.application.category.CategoryRepository;
import com.myfinance.ledger.application.expense.dto.ExpenseCommand;
import com.myfinance.ledger.application.expense.dto.ExpenseResult;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.category.ExpenseType;
import com.myfinance.ledger.domain.expense.Expense;
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
    public ExpenseResult createExpense(ExpenseCommand command) {
        Category majorCategory = categoryRepository.findById(command.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));

        Category minorCategory = null;
        if (command.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(command.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }

        Expense expense = Expense.of(
                command.getExpenseDate(),
                command.getExpenseType(),
                majorCategory,
                minorCategory,
                command.getContent(),
                command.getPaymentAmount(),
                command.getDiscountAmount(),
                command.getActualAmount(),
                command.getRemark()
        );

        Expense saved = expenseRepository.save(expense);
        return ExpenseResult.from(saved);
    }

    /**
     * 지출 수정
     */
    @Transactional
    public ExpenseResult updateExpense(Long id, ExpenseCommand command) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다"));

        Category majorCategory = categoryRepository.findById(command.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));

        Category minorCategory = null;
        if (command.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(command.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }

        expense.update(
                command.getExpenseDate(),
                majorCategory,
                minorCategory,
                command.getContent(),
                command.getPaymentAmount(),
                command.getDiscountAmount(),
                command.getActualAmount(),
                command.getRemark()
        );

        return ExpenseResult.from(expense);
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
    public List<ExpenseResult> getMonthlyExpenses(int year, int month, ExpenseType expenseType) {
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
                .map(ExpenseResult::from)
                .toList();
    }

    /**
     * 지출 상세 조회
     */
    public ExpenseResult getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지출입니다"));
        return ExpenseResult.from(expense);
    }
}