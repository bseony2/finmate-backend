package com.myfinance.ledger.infrastructure.persistence.income;

import com.myfinance.ledger.domain.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface JpaIncomeRepository extends JpaRepository<Income, Long> {
    
    List<Income> findByIncomeDateBetweenOrderByIncomeDateDesc(LocalDate startDate, LocalDate endDate);

    List<Income> findByMajorCategoryIdOrderByIncomeDateDesc(Long majorCategoryId);

    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.majorCategory.id = :majorCategoryId")
    BigDecimal getTotalAmountByMajorCategory(@Param("majorCategoryId") Long majorCategoryId);
}
