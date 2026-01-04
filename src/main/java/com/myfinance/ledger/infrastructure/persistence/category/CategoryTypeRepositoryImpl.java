package com.myfinance.ledger.infrastructure.persistence.category;

import com.myfinance.ledger.application.category.CategoryTypeRepository;
import com.myfinance.ledger.domain.category.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryTypeRepositoryImpl implements CategoryTypeRepository {

    private final JpaCategoryTypeRepository jpaCategoryTypeRepository;

    @Override
    public Optional<CategoryType> findByName(String name) {
        return jpaCategoryTypeRepository.findByName(name);
    }

    @Override
    public CategoryType save(CategoryType categoryType) {
        return jpaCategoryTypeRepository.save(categoryType);
    }

    @Override
    public List<CategoryType> findAll() {
        return jpaCategoryTypeRepository.findAll();
    }

    @Override
    public Optional<CategoryType> findById(Long id) {
        return jpaCategoryTypeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaCategoryTypeRepository.deleteById(id);
    }
}

