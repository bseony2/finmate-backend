package com.myfinance.ledger.infrastructure.persistence.category;

import com.myfinance.ledger.application.category.CategoryRepository;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.category.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;


    @Override
    public Category save(Category category) {
        return jpaCategoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpaCategoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return jpaCategoryRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaCategoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByCategoryTypeAndParentIsNull(CategoryType categoryType) {
        return jpaCategoryRepository.findByCategoryTypeAndParentIsNull(categoryType);
    }

    @Override
    public List<Category> findByCategoryType(CategoryType categoryType) {
        return jpaCategoryRepository.findByCategoryType(categoryType);
    }

    @Override
    public List<Category> findByCategoryTypeIn(List<CategoryType> categoryTypes) {
        return jpaCategoryRepository.findByCategoryTypeIn(categoryTypes);
    }

    @Override
    public List<Category> findByParent(Category parent) {
        return jpaCategoryRepository.findByParent(parent);
    }
}
