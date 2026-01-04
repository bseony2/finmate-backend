package com.myfinance.ledger.application.category;

import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.category.CategoryType;

import java.util.List;
import java.util.Optional;

/**
 * Category 영속성을 위한 Port 인터페이스
 * Application 레이어가 필요로 하는 Repository 기능을 정의
 */
public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);

    List<Category> findByCategoryTypeAndParentIsNull(CategoryType categoryType);

    List<Category> findByCategoryType(CategoryType categoryType);

    List<Category> findByCategoryTypeIn(List<CategoryType> categoryTypes);

    List<Category> findByParent(Category parent);
}