package com.myfinance.ledger.infrastructure.persistence.expense;

import com.myfinance.ledger.application.expense.ExpenseRepository;
import com.myfinance.ledger.domain.category.ExpenseType;
import com.myfinance.ledger.domain.expense.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final JpaExpenseRepository jpaExpenseRepository;

    @Override
    public Expense save(Expense expense) {
        return jpaExpenseRepository.save(expense);
    }

    @Override
    public Optional<Expense> findById(Long id) {
        return jpaExpenseRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaExpenseRepository.deleteById(id);
    }

    @Override
    public List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate) {
        return jpaExpenseRepository.findByExpenseDateBetween(startDate, endDate);
    }

    @Override
    public List<Expense> findByExpenseType(ExpenseType expenseType) {
        return jpaExpenseRepository.findByExpenseType(expenseType);
    }

    @Override
    public List<Expense> findByExpenseDateBetweenAndExpenseType(LocalDate startDate, LocalDate endDate, ExpenseType expenseType) {
        return jpaExpenseRepository.findByExpenseDateBetweenAndExpenseType(startDate, endDate, expenseType);
    }

    @Override
    public List<Expense> findByExpenseDateBetweenOrderByExpenseDateAsc(LocalDate startDate, LocalDate endDate) {
        return jpaExpenseRepository.findByExpenseDateBetweenOrderByExpenseDateAsc(startDate, endDate);
    }
}
