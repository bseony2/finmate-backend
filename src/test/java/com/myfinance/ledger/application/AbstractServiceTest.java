package com.myfinance.ledger.application;

import com.myfinance.ledger.application.category.CategoryRepository;
import com.myfinance.ledger.application.category.CategoryTypeRepository;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.category.CategoryType;
import com.myfinance.ledger.application.savings.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;

abstract class AbstractServiceTest {

    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected CategoryTypeRepository categoryTypeRepository;
    @Autowired
    protected SavingsService savingsService;

    protected CategoryType categoryType;
    protected Category majorCategory;
    protected Category minorCategory;

    protected void setInitCategory(String typeName, String majorName, String minorName) {


        this.categoryType = CategoryType.of(typeName);
        categoryTypeRepository.save(this.categoryType);
        majorCategory = createCategory(majorName, null);
        minorCategory = createCategory(minorName, majorCategory);
    }

    protected Category createCategory(String name, Category parent) {

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
