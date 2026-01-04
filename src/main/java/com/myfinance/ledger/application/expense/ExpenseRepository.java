package com.myfinance.ledger.application.expense;

import com.myfinance.ledger.domain.category.ExpenseType;
import com.myfinance.ledger.domain.expense.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    Expense save(Expense expense);

    Optional<Expense> findById(Long id);

    void deleteById(Long id);

    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByExpenseType(ExpenseType expenseType);

    List<Expense> findByExpenseDateBetweenAndExpenseType(
            LocalDate startDate,
            LocalDate endDate,
            ExpenseType expenseType
    );

    List<Expense> findByExpenseDateBetweenOrderByExpenseDateAsc(
            LocalDate startDate,
            LocalDate endDate
    );
}
