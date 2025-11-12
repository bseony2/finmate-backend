package com.myfinance.service;

import com.myfinance.domain.Category;
import com.myfinance.domain.Income;
import com.myfinance.dto.request.IncomeRequest;
import com.myfinance.dto.response.IncomeResponse;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 수입 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 수입 생성
     */
    @Transactional
    public IncomeResponse createIncome(IncomeRequest request) {
        Category majorCategory = findCategoryById(request.getMajorCategoryId());
        Category minorCategory = request.getMinorCategoryId() != null
                ? findCategoryById(request.getMinorCategoryId())
                : null;

        Income income = Income.of(
                majorCategory,
                minorCategory,
                request.getContent(),
                request.getAmount()
        );

        Income savedIncome = incomeRepository.save(income);
        return IncomeResponse.from(savedIncome);
    }

    /**
     * 수입 단건 조회
     */
    public IncomeResponse getIncome(Long id) {
        Income income = findIncomeById(id);
        return IncomeResponse.from(income);
    }

    /**
     * 전체 수입 목록 조회
     */
    public List<IncomeResponse> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .map(IncomeResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 수입 수정
     */
    @Transactional
    public IncomeResponse updateIncome(Long id, IncomeRequest request) {
        Income income = findIncomeById(id);

        Category majorCategory = findCategoryById(request.getMajorCategoryId());
        Category minorCategory = request.getMinorCategoryId() != null
                ? findCategoryById(request.getMinorCategoryId())
                : null;

        income.update(
                majorCategory,
                minorCategory,
                request.getContent(),
                request.getAmount()
        );

        return IncomeResponse.from(income);
    }

    /**
     * 수입 삭제
     */
    @Transactional
    public void deleteIncome(Long id) {
        Income income = findIncomeById(id);
        incomeRepository.delete(income);
    }

    /**
     * 카테고리 ID로 카테고리 조회
     */
    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다: " + categoryId));
    }

    /**
     * 수입 ID로 수입 조회
     */
    private Income findIncomeById(Long id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수입입니다: " + id));
    }
}