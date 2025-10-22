package com.myfinance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_type_id", nullable = false)
    private CategoryType categoryType;

    @Column(name = "display_order")
    private Integer displayOrder;

    public boolean isTopLevel() {
        return parent == null;
    }

    public static Category createTopLevelCategory(CategoryType categoryType, String name) {
        Category category = new Category();
        category.name = name;
        category.categoryType = categoryType;

        return category;
    }

    public Category withDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;

        return this;
    }

    private static void validateName(String name)
    {
        if(name==null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("카테고리의 이름은 필수입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(parent, category.parent) && Objects.equals(categoryType, category.categoryType) && Objects.equals(displayOrder, category.displayOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parent, categoryType, displayOrder);
    }
}
