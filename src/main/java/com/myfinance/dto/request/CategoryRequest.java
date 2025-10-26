package com.myfinance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    private String name;
    private String categoryTypeName; // "수입" 또는 "지출"
    private Long parentId; // 최상위 카테고리면 null
    private Integer displayOrder;
}