package com.myfinance.ledger.application.savings;

import com.myfinance.ledger.application.category.CategoryRepository;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.savings.Savings;
import com.myfinance.ledger.interfaces.rest.savings.dto.SavingsRequest;
import com.myfinance.ledger.interfaces.rest.savings.dto.SavingsResponse;
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


    public SavingsResponse createSavings(SavingsRequest request) {
        Category majorCategory = getMajorCategory(request);

        Category minorCategory = getMinorCategory(request);

        Savings savings = Savings.of(
            request.getSavingDate()
            , majorCategory
            , minorCategory
            , request.getAcctNo()
            , request.getContent()
            , request.getAmount()
        );

        Savings saved = savingsRepository.save(savings);

        return SavingsResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public SavingsResponse getSavingsById(Long id) {
        Savings savings = savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        return SavingsResponse.from(savings);
    }

    @Transactional(readOnly = true)
    public List<SavingsResponse> getMonthlySavings(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Savings> savings = savingsRepository.findBySavingDateBetweenOrderBySavingDateDesc(startDate, endDate);

        return savings.stream().map(SavingsResponse::from).toList();
    }

    public SavingsResponse updateSavings(Long id, SavingsRequest request) {
        Savings savings = savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        Category majorCategory = getMajorCategory(request);

        Category minorCategory = getMinorCategory(request);

        savings.update(
                request.getSavingDate(),
                majorCategory,
                minorCategory,
                request.getAcctNo(),
                request.getContent(),
                request.getAmount()
        );

        return SavingsResponse.from(savings);
    }

    public void deleteSavings(Long id) {
        Savings savings = savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        savingsRepository.deleteById(savings.getId());
    }

    private Category getMajorCategory(SavingsRequest request) {
        return categoryRepository.findById(request.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));
    }

    private Category getMinorCategory(SavingsRequest request) {
        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }
        return minorCategory;
    }
}
