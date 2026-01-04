package com.myfinance.ledger.application.income;

import com.myfinance.ledger.application.category.CategoryRepository;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.income.Income;
import com.myfinance.ledger.infrastructure.persistence.income.IncomeRepository;
import com.myfinance.ledger.interfaces.rest.income.dto.IncomeRequest;
import com.myfinance.ledger.interfaces.rest.income.dto.IncomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
                request.getIncomeDate(),
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
                .toList();
    }

    public List<IncomeResponse> getMonthlyIncomes(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return incomeRepository.findByIncomeDateBetweenOrderByIncomeDateDesc(startDate, endDate)
                .stream()
                .map(IncomeResponse::from)
                .toList();
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
                request.getIncomeDate(),
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