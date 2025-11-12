package com.myfinance.repository;

import com.myfinance.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 수입 리포지토리
 */
public interface IncomeRepository extends JpaRepository<Income, Long> {

    /**
     * 날짜 범위로 수입 목록 조회 (생성일 기준)
     */
    List<Income> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * 특정 카테고리의 수입 목록 조회
     */
    List<Income> findByMajorCategoryIdOrderByCreatedAtDesc(Long majorCategoryId);

    /**
     * 대분류 카테고리별 총 수입 금액 조회
     */
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.majorCategory.id = :majorCategoryId")
    BigDecimal getTotalAmountByMajorCategory(@Param("majorCategoryId") Long majorCategoryId);
}