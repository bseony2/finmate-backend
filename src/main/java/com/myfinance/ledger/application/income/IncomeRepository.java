package com.myfinance.ledger.application.income;

import com.myfinance.ledger.domain.income.Income;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository {

    Income save(Income income);

    Optional<Income> findById(Long id);

    List<Income> findAll();

    void deleteById(Long id);

    List<Income> findByIncomeDateBetweenOrderByIncomeDateDesc(LocalDate startDate, LocalDate endDate);

    List<Income> findByMajorCategoryIdOrderByIncomeDateDesc(Long majorCategoryId);

    BigDecimal getTotalAmountByMajorCategory(@Param("majorCategoryId") Long majorCategoryId);
}
