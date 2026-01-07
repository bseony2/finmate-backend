package com.myfinance.ledger.application.category;

import com.myfinance.ledger.application.category.dto.CategoryCommand;
import com.myfinance.ledger.application.category.dto.CategoryResult;
import com.myfinance.ledger.domain.category.Category;
import com.myfinance.ledger.domain.category.CategoryType;
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
    public List<CategoryResult> getCategoryTree(List<String> categoryTypeNames) {
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
                            .map(category -> CategoryResult.fromWithChildren(category, categories));
                })
                .toList();
    }

    /**
     * 카테고리 생성
     */
    @Transactional
    public CategoryResult createCategory(CategoryCommand command) {
        CategoryType categoryType = categoryTypeRepository.findByName(command.getCategoryTypeName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 타입입니다: " + command.getCategoryTypeName()));

        Category category;

        if (command.getParentId() == null) {
            // 최상위 카테고리 생성
            category = Category.createTopLevelCategory(categoryType, command.getName());
        } else {
            // 하위 카테고리 생성
            Category parent = categoryRepository.findById(command.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부모 카테고리입니다"));
            category = Category.createSubCategory(categoryType, command.getName(), parent);
        }

        // displayOrder 설정
        if (command.getDisplayOrder() != null) {
            category = category.withDisplayOrder(command.getDisplayOrder());
        }

        Category saved = categoryRepository.save(category);
        return CategoryResult.from(saved);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryResult updateCategory(Long id, CategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다"));

        // 이름 수정
        if (command.getName() != null) {
            category.changeName(command.getName());
        }

        // displayOrder 수정
        if (command.getDisplayOrder() != null) {
            category.changeDisplayOrder(command.getDisplayOrder());
        }

        return CategoryResult.from(category);
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