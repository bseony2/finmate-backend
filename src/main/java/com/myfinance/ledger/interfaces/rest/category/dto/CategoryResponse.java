package com.myfinance.ledger.interfaces.rest.category.dto;

import com.myfinance.ledger.application.category.dto.CategoryResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    public static CategoryResponse from(CategoryResult result) {

        List<CategoryResponse> children = result.getChildren() != null
                ? result.getChildren().stream()
                .map(CategoryResponse::from)
                .toList()
                : List.of();

        return CategoryResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .categoryTypeName(result.getCategoryTypeName())
                .parentId(result.getParentId())
                .displayOrder(result.getDisplayOrder())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .children(children)
                .build();
    }

}