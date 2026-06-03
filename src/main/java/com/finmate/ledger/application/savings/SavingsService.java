package com.finmate.ledger.application.savings;

import com.finmate.ledger.application.category.CategoryRepository;
import com.finmate.ledger.application.savings.dto.SavingsCommand;
import com.finmate.ledger.application.savings.dto.SavingsResult;
import com.finmate.ledger.domain.category.Category;
import com.finmate.ledger.domain.savings.Savings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SavingsService {

    private final SavingsRepository savingsRepository;
    private final CategoryRepository categoryRepository;


    public SavingsResult createSavings(SavingsCommand command) {
        Category majorCategory = getMajorCategory(command);

        Category minorCategory = getMinorCategory(command);

        Savings savings = Savings.of(
            command.getSavingDate()
            , majorCategory
            , minorCategory
            , command.getAcctNo()
            , command.getContent()
            , command.getAmount()
        );

        Savings saved = savingsRepository.save(savings);

        return SavingsResult.from(saved);
    }

    @Transactional(readOnly = true)
    public SavingsResult getSavingsById(Long id) {
        Savings savings = savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        return SavingsResult.from(savings);
    }

    @Transactional(readOnly = true)
    public List<SavingsResult> getMonthlySavings(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Savings> savings = savingsRepository.findBySavingDateBetweenOrderBySavingDateDesc(startDate, endDate);

        return savings.stream().map(SavingsResult::from).toList();
    }

    public SavingsResult updateSavings(Long id, SavingsCommand command) {
        Savings savings = savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        Category majorCategory = getMajorCategory(command);

        Category minorCategory = getMinorCategory(command);

        savings.update(
                command.getSavingDate(),
                majorCategory,
                minorCategory,
                command.getAcctNo(),
                command.getContent(),
                command.getAmount()
        );

        return SavingsResult.from(savings);
    }

    public void deleteSavings(Long id) {
        Savings savings = savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        savingsRepository.deleteById(savings.getId());
    }

    private Category getMajorCategory(SavingsCommand command) {
        return categoryRepository.findById(command.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));
    }

    private Category getMinorCategory(SavingsCommand command) {
        Category minorCategory = null;
        if (command.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(command.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }
        return minorCategory;
    }
}
