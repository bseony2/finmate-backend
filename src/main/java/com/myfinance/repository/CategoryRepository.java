package com.myfinance.repository;

import com.myfinance.domain.Category;
import com.myfinance.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findByCategoryTypeAndParentIsNull(CategoryType categoryType);

    List<Category> findByCategoryType(CategoryType categoryType);

    List<Category> findByCategoryTypeIn(List<CategoryType> categoryTypes);

    List<Category> findByParent(Category parent);
}
