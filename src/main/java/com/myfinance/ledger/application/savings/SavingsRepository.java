package com.myfinance.ledger.application.savings;

import com.myfinance.ledger.domain.savings.Savings;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SavingsRepository {

    Savings save(Savings savings);

    Optional<Savings> findById(Long id);

    void deleteById(Long id);

    List<Savings> findBySavingDateBetweenOrderBySavingDateDesc(LocalDate start, LocalDate end);
}
