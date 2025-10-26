package com.myfinance.service;

import com.myfinance.domain.CategoryType;
import com.myfinance.repository.CategoryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryTypeService {

    private final CategoryTypeRepository categoryTypeRepository;

    /**
     * 카테고리 타입 생성
     */
    @Transactional
    public CategoryType createCategoryType(String name) {
        // 중복 체크
        categoryTypeRepository.findByName(name)
                .ifPresent(type -> {
                    throw new IllegalArgumentException("이미 존재하는 카테고리 타입입니다: " + name);
                });

        CategoryType categoryType = CategoryType.of(name);
        return categoryTypeRepository.save(categoryType);
    }

    /**
     * 모든 카테고리 타입 조회
     */
    public List<CategoryType> getAllCategoryTypes() {
        return categoryTypeRepository.findAll();
    }

    /**
     * 카테고리 타입 삭제
     */
    @Transactional
    public void deleteCategoryType(Integer id) {
        CategoryType categoryType = categoryTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 타입입니다"));

        categoryTypeRepository.delete(categoryType);
    }
}