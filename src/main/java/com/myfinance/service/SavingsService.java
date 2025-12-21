package com.myfinance.service;

import com.myfinance.domain.Category;
import com.myfinance.domain.Savings;
import com.myfinance.dto.request.SavingsRequest;
import com.myfinance.dto.response.SavingsResponse;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.SavingsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SavingsService {

    private final SavingsRepository savingsRepository;
    private final CategoryRepository categoryRepository;


    public SavingsResponse createSavings(SavingsRequest request) {
        Category majorCategory = categoryRepository.findById(request.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));

        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }
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

    public Savings getSavingsById(long id) {
        return savingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));
    }

    public List<SavingsResponse> getMonthlySavings(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.getDayOfMonth());

        List<Savings> savings = savingsRepository.findBySavingDateBetweenOrderBySavingDateDesc(startDate, endDate);

        return savings.stream().map(SavingsResponse::from).toList();
    }

    public SavingsResponse updateSavings(SavingsRequest request) {
        Savings savings = savingsRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 저축입니다"));

        Category majorCategory = categoryRepository.findById(request.getMajorCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대분류 카테고리입니다"));

        Category minorCategory = null;
        if (request.getMinorCategoryId() != null) {
            minorCategory = categoryRepository.findById(request.getMinorCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소분류 카테고리입니다"));
        }

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
}
