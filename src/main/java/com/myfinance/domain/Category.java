package com.myfinance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Category extends BaseEntity{

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
        validateName(name);
        validateCategoryType(categoryType);

        Category category = new Category();
        category.name = name;
        category.categoryType = categoryType;

        return category;
    }

    public static Category createSubCategory(CategoryType categoryType, String name, Category parent) {
        validateName(name);
        validateCategoryType(categoryType);
        validateParent(parent);
        validateParentCategory(categoryType, parent.getCategoryType());

        Category category = new Category();
        category.name = name;
        category.categoryType = categoryType;
        category.parent = parent;

        return category;
    }

    @Deprecated
    public static Category of(CategoryType categoryType, String name, Category parent) {
        Category category = new Category();
        category.categoryType = categoryType;
        category.name = name;
        category.parent = parent;

        return category;
    }

    public Category withDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;

        return this;
    }

    // Category.java에 추가
    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    public void changeDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    private static void validateName(String name)
    {
        if(name==null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("카테고리의 이름은 필수입니다.");
        }
    }

    private static void validateCategoryType(CategoryType categoryType) {
        if (categoryType == null) {
            throw new IllegalArgumentException("카테고리 타입은 필수입니다.");
        }
    }
    
    private static void validateParent(Category parent) {
        if (parent == null) {
            throw new IllegalArgumentException("하위 카테고리는 상위카테고리가 필수입니다");
        }
    }

    private static void validateParentCategory(CategoryType categoryType, CategoryType parentCategoryType) {
        if (!Objects.equals(categoryType.getId(), parentCategoryType.getId())) {
            throw new IllegalArgumentException("하위 카테고리는 부모와 같은 타입이어야 합니다.");
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
