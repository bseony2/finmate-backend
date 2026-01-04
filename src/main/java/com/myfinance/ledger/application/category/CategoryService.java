package com.myfinance.ledger.application.category;

import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.category.CategoryType;
import com.myfinance.ledger.interfaces.rest.category.dto.CategoryRequest;
import com.myfinance.ledger.interfaces.rest.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryTypeRepository categoryTypeRepository;

    /**
     * 여러 타입의 카테고리 계층 구조 조회
     */
    public List<CategoryResponse> getCategoryTree(List<String> categoryTypeNames) {
        // 1. 카테고리 타입들 조회
        List<CategoryType> categoryTypes = categoryTypeNames.stream()
                .map(name -> categoryTypeRepository.findByName(name)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 타입입니다: " + name)))
                .toList();

        // 2. 해당 타입들의 모든 카테고리 조회 (1번의 쿼리)
        List<Category> allCategories = categoryRepository.findByCategoryTypeIn(categoryTypes);

        // 3. 타입별로 그룹핑
        Map<CategoryType, List<Category>> categoryByType = allCategories.stream()
                .collect(Collectors.groupingBy(Category::getCategoryType));

        // 4. 각 타입별로 트리 구조 생성
        return categoryByType.entrySet().stream()
                .flatMap(entry -> {
                    List<Category> categories = entry.getValue();
                    return categories.stream()
                            .filter(Category::isTopLevel)
                            .map(category -> CategoryResponse.fromWithChildren(category, categories));
                })
                .toList();
    }

    /**
     * 카테고리 생성
     */
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        CategoryType categoryType = categoryTypeRepository.findByName(request.getCategoryTypeName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 타입입니다: " + request.getCategoryTypeName()));

        Category category;

        if (request.getParentId() == null) {
            // 최상위 카테고리 생성
            category = Category.createTopLevelCategory(categoryType, request.getName());
        } else {
            // 하위 카테고리 생성
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다"));
            category = Category.createSubCategory(categoryType, request.getName(), parent);
        }

        // displayOrder 설정
        if (request.getDisplayOrder() != null) {
            category = category.withDisplayOrder(request.getDisplayOrder());
        }

        Category saved = categoryRepository.save(category);
        return CategoryResponse.from(saved);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다"));

        // 이름 수정
        if (request.getName() != null) {
            category.changeName(request.getName());
        }

        // displayOrder 수정
        if (request.getDisplayOrder() != null) {
            category.changeDisplayOrder(request.getDisplayOrder());
        }

        return CategoryResponse.from(category);
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다"));

        // 하위 카테고리가 있는지 확인
        List<Category> children = categoryRepository.findByParent(category);
        if (!children.isEmpty()) {
            throw new IllegalStateException("하위 카테고리가 있는 카테고리는 삭제할 수 없습니다");
        }

        categoryRepository.deleteById(category.getId());
    }
}