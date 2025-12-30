package com.myfinance.ledger.infrastructure.persistence.income;

import com.myfinance.ledger.domain.income.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 수입 리포지토리
 */
public interface IncomeRepository extends JpaRepository<Income, Long> {

    /**
     * 날짜 범위로 수입 목록 조회 (수입일자 기준)
     */
    List<Income> findByIncomeDateBetweenOrderByIncomeDateDesc(LocalDate startDate, LocalDate endDate);

    /**
     * 특정 카테고리의 수입 목록 조회
     */
    List<Income> findByMajorCategoryIdOrderByIncomeDateDesc(Long majorCategoryId);

    /**
     * 대분류 카테고리별 총 수입 금액 조회
     */
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.majorCategory.id = :majorCategoryId")
    BigDecimal getTotalAmountByMajorCategory(@Param("majorCategoryId") Long majorCategoryId);
}