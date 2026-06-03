package com.finmate.ledger.application.category;

import com.finmate.ledger.domain.category.CategoryType;

import java.util.List;
import java.util.Optional;

public interface CategoryTypeRepository {
    Optional<CategoryType> findByName(String name);

    CategoryType save(CategoryType categoryType);

    List<CategoryType> findAll();

    Optional<CategoryType> findById(Long id);

    void deleteById(Long id);

}
