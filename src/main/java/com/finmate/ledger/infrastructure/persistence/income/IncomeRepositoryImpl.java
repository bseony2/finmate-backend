package com.finmate.ledger.infrastructure.persistence.income;

import com.finmate.ledger.application.income.IncomeRepository;
import com.finmate.ledger.domain.income.Income;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IncomeRepositoryImpl implements IncomeRepository {

    private final JpaIncomeRepository jpaIncomeRepository;

    @Override
    public Income save(Income income) {
        return jpaIncomeRepository.save(income);
    }

    @Override
    public Optional<Income> findById(Long id) {
        return jpaIncomeRepository.findById(id);
    }

    @Override
    public List<Income> findAll() {
        return jpaIncomeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaIncomeRepository.deleteById(id);
    }

    @Override
    public List<Income> findByIncomeDateBetweenOrderByIncomeDateDesc(LocalDate startDate, LocalDate endDate) {
        return jpaIncomeRepository.findByIncomeDateBetweenOrderByIncomeDateDesc(startDate, endDate);
    }

    @Override
    public List<Income> findByMajorCategoryIdOrderByIncomeDateDesc(Long majorCategoryId) {
        return jpaIncomeRepository.findByMajorCategoryIdOrderByIncomeDateDesc(majorCategoryId);
    }

    @Override
    public BigDecimal getTotalAmountByMajorCategory(Long majorCategoryId) {
        return jpaIncomeRepository.getTotalAmountByMajorCategory(majorCategoryId);
    }
}
