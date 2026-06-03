package com.finmate.ledger.infrastructure.persistence.category;

import com.finmate.ledger.domain.category.Category;
import com.finmate.ledger.domain.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface JpaCategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByCategoryTypeAndParentIsNull(CategoryType categoryType);

    List<Category> findByCategoryType(CategoryType categoryType);

    List<Category> findByCategoryTypeIn(List<CategoryType> categoryTypes);

    List<Category> findByParent(Category parent);
}