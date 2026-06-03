package com.finmate.ledger.infrastructure.persistence.savings;

import com.finmate.ledger.domain.savings.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaSavingsRepository extends JpaRepository<Savings, Long> {
    List<Savings> findBySavingDateBetweenOrderBySavingDateDesc(LocalDate start, LocalDate end);
}
