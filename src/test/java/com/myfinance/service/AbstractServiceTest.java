package com.myfinance.service;

import com.myfinance.domain.Category;
import com.myfinance.domain.CategoryType;
import com.myfinance.repository.CategoryRepository;
import com.myfinance.repository.CategoryTypeRepository;
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
