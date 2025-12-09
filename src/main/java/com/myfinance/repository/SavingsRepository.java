package com.myfinance.repository;

import com.myfinance.domain.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

}
