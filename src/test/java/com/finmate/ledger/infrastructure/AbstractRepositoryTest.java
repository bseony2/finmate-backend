package com.finmate.ledger.infrastructure;

import com.finmate.ledger.application.category.CategoryRepository;
import com.finmate.ledger.application.category.CategoryTypeRepository;
import com.finmate.ledger.domain.category.Category;
import com.finmate.ledger.domain.category.CategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

abstract class AbstractRepositoryTest {

    @Autowired
    protected CategoryTypeRepository categoryTypeRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    protected CategoryType incomeType;
    protected CategoryType expenseType;
    protected CategoryType savingsType;
    protected Category majorCategory;
    protected Category minorCategory;

    @BeforeEach
    void setUpCategoryTypes() {
        // 정적 팩토리 메서드로 생성
        incomeType = categoryTypeRepository.save(
                CategoryType.of("테스트_수입")
        );
        expenseType = categoryTypeRepository.save(
                CategoryType.of("테스트_지출")
        );
        savingsType = categoryTypeRepository.save(
                CategoryType.of("테스트_저축")
        );
    }

    protected Category createCategory(CategoryType categoryType, String name, Category parent) {

        Category category;
        if (parent == null) {
            category = Category.createTopLevelCategory(categoryType, name);
        } else {
            category = Category.createSubCategory(categoryType, name, parent);
        }

        categoryRepository.save(category);

        return category;
    }
}