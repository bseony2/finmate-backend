package com.finmate.ledger.application.income;

import com.finmate.ledger.application.category.CategoryRepository;
import com.finmate.ledger.application.income.dto.IncomeCommand;
import com.finmate.ledger.application.income.dto.IncomeResult;
import com.finmate.ledger.domain.category.Category;
import com.finmate.ledger.domain.income.Income;
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
    public IncomeResult createIncome(IncomeCommand command) {
        Category majorCategory = findCategoryById(command.getMajorCategoryId());
        Category minorCategory = command.getMinorCategoryId() != null
                ? findCategoryById(command.getMinorCategoryId())
                : null;

        Income income = Income.of(
                command.getIncomeDate(),
                majorCategory,
                minorCategory,
                command.getContent(),
                command.getAmount()
        );

        Income savedIncome = incomeRepository.save(income);
        return IncomeResult.from(savedIncome);
    }

    /**
     * 수입 단건 조회
     */
    public IncomeResult getIncome(Long id) {
        Income income = findIncomeById(id);
        return IncomeResult.from(income);
    }

    /**
     * 전체 수입 목록 조회
     */
    public List<IncomeResult> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .map(IncomeResult::from)
                .toList();
    }

    public List<IncomeResult> getMonthlyIncomes(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return incomeRepository.findByIncomeDateBetweenOrderByIncomeDateDesc(startDate, endDate)
                .stream()
                .map(IncomeResult::from)
                .toList();
    }

    /**
     * 수입 수정
     */
    @Transactional
    public IncomeResult updateIncome(Long id, IncomeCommand command) {
        Income income = findIncomeById(id);

        Category majorCategory = findCategoryById(command.getMajorCategoryId());
        Category minorCategory = command.getMinorCategoryId() != null
                ? findCategoryById(command.getMinorCategoryId())
                : null;

        income.update(
                command.getIncomeDate(),
                majorCategory,
                minorCategory,
                command.getContent(),
                command.getAmount()
        );

        return IncomeResult.from(income);
    }

    /**
     * 수입 삭제
     */
    @Transactional
    public void deleteIncome(Long id) {
        Income income = findIncomeById(id);
        incomeRepository.deleteById(income.getId());
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