package com.finmate.ledger.application.category.dto;

import com.finmate.ledger.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CategoryResult {

    private Long id;
    private String name;
    private String categoryTypeName;
    private Long parentId;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryResult> children;

    public static CategoryResult from(Category category) {
        return CategoryResult.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryTypeName(category.getCategoryType().getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .displayOrder(category.getDisplayOrder())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .children(List.of())
                .build();
    }

    public static CategoryResult fromWithChildren(Category category, List<Category> allCategories) {
        List<CategoryResult> children = allCategories.stream()
                .filter(c -> c.getParent() != null && c.getParent().getId().equals(category.getId()))
                .map(child -> CategoryResult.fromWithChildren(child, allCategories))  // 재귀
                .toList();

        return CategoryResult.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryTypeName(category.getCategoryType().getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .displayOrder(category.getDisplayOrder())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .children(children)
                .build();
    }
}
