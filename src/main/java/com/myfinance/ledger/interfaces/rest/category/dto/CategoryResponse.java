package com.myfinance.ledger.interfaces.rest.category.dto;

import com.myfinance.ledger.domain.category.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String categoryTypeName;
    private Long parentId;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryResponse> children;

    // Entity -> DTO 변환
    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .categoryTypeName(category.getCategoryType().getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .displayOrder(category.getDisplayOrder())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public static CategoryResponse fromWithChildren(Category category, List<Category> allCategories) {
        List<CategoryResponse> children = allCategories.stream()
                .filter(c -> c.getParent() != null && c.getParent().getId().equals(category.getId()))
                .map(CategoryResponse::from)
                .toList();

        return CategoryResponse.builder()
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