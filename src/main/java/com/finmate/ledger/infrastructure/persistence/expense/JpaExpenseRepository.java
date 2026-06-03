package com.finmate.ledger.infrastructure.persistence.expense;

import com.finmate.ledger.domain.expense.Expense;
import com.finmate.ledger.domain.category.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * 특정 기간의 지출 조회
     */
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * 특정 타입의 지출 조회
     */
    List<Expense> findByExpenseType(ExpenseType expenseType);

    /**
     * 특정 기간 + 특정 타입 지출 조회
     */
    List<Expense> findByExpenseDateBetweenAndExpenseType(
            LocalDate startDate,
            LocalDate endDate,
            ExpenseType expenseType
    );

    /**
     * 특정 월의 지출 조회
     */
    List<Expense> findByExpenseDateBetweenOrderByExpenseDateAsc(
            LocalDate startDate,
            LocalDate endDate
    );
}