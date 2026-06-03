package com.finmate.ledger.infrastructure.persistence.savings;

import com.finmate.ledger.application.savings.SavingsRepository;
import com.finmate.ledger.domain.savings.Savings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SavingsRepositoryImpl implements SavingsRepository {

    private final JpaSavingsRepository jpaSavingsRepository;
    @Override
    public Savings save(Savings savings) {
        return jpaSavingsRepository.save(savings);
    }

    @Override
    public Optional<Savings> findById(Long id) {
        return jpaSavingsRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaSavingsRepository.deleteById(id);
    }

    @Override
    public List<Savings> findBySavingDateBetweenOrderBySavingDateDesc(LocalDate start, LocalDate end) {
        return jpaSavingsRepository.findBySavingDateBetweenOrderBySavingDateDesc(start, end);
    }
}
