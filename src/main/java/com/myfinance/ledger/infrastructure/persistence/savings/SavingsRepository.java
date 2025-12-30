package com.myfinance.ledger.infrastructure.persistence.savings;

import com.myfinance.ledger.domain.savings.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findBySavingDateBetweenOrderBySavingDateDesc(LocalDate start, LocalDate end);
}
