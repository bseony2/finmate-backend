package com.myfinance.repository;

import com.myfinance.domain.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findBySavingDateBetweenOrderBySavingDateDesc(LocalDate start, LocalDate end);
}
