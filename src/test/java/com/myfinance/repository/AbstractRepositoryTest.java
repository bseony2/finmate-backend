package com.myfinance.repository;

import com.myfinance.domain.CategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
abstract class AbstractRepositoryTest {

    @Autowired
    protected CategoryTypeRepository categoryTypeRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    protected CategoryType incomeType;
    protected CategoryType expenseType;
    protected CategoryType savingsType;

    @BeforeEach
    void setUpCategoryTypes() {
        // 정적 팩토리 메서드로 생성
        incomeType = categoryTypeRepository.save(
                CategoryType.of("수입")
        );
        expenseType = categoryTypeRepository.save(
                CategoryType.of("지출")
        );
        savingsType = categoryTypeRepository.save(
                CategoryType.of("저축")
        );
    }
}