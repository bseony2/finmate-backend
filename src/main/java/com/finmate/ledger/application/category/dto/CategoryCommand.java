package com.finmate.ledger.application.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryCommand {

    private String name;
    private String categoryTypeName; // "수입" 또는 "지출"
    private Long parentId; // 최상위 카테고리면 null
    private Integer displayOrder;
}
