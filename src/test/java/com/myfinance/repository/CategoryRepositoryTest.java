package com.myfinance.repository;

import com.myfinance.domain.Category;
import com.myfinance.domain.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest{

    @Autowired
    CategoryTypeRepository categoryTypeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 타입을 저장할때 타입명 기입 누락")
    void withoutCategoryTypeName() {

        // given
        String name = "";

        // when && then
        assertThatThrownBy(() -> CategoryType.of(name))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    @DisplayName("카테고리 생성 및 조회")
    void createAndFindCategory(){
        // given
        CategoryType incomeType = CategoryType.of("수입");
        categoryTypeRepository.save(incomeType);

        // given
        Category newCategory = Category.createTopLevelCategory(incomeType, "급여").withDisplayOrder(1);

        // when
        Category savedCategory = categoryRepository.save(newCategory);
        Long id = savedCategory.getId();
        //then
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getCategoryType()).isEqualTo(incomeType);
        assertThat(savedCategory.getName()).isEqualTo("급여");
        assertThat(categoryRepository.findById(id)).isPresent();

    }

    @Test
    @DisplayName("하위 카테고리는 부모 카테고리가 필수")
    void subCategoryNeedTopCategory() {
        // given
        CategoryType incomeType = CategoryType.of("수입");

        //when
        //then
        assertThatThrownBy(() -> Category.createSubCategory(incomeType, "월급", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상위 카테고리와 하위 카테고리가 동일한지 테스트")
    void topCategoryAndSubCategoryHasSameCategoryType() {
        // given
        CategoryType topCategoryType = CategoryType.of("수입");
        CategoryType subCategoryType = CategoryType.of("지출");

        categoryTypeRepository.save(topCategoryType);
        categoryTypeRepository.save(subCategoryType);

        Category topCategory = Category.createTopLevelCategory(topCategoryType, "금융소득");

        // when
        // then
        assertThatThrownBy(() -> Category.createSubCategory(subCategoryType, "식사", topCategory))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("하위 카테고리는 부모와 같은 타입이어야 합니다.");
    }

    @Test
    @DisplayName("하위 카테고리 생성 테스트")
    void createSubCategory() {
        // given
        CategoryType categoryType = CategoryType.of("지출");
        categoryTypeRepository.save(categoryType);

        Category topCategory = Category.createTopLevelCategory(categoryType, "의류미용");

        // when
        Category newCategory = Category.createSubCategory(categoryType, "미용실", topCategory);
        
        // then
        assertThat(newCategory).isNotNull();
        assertThat(newCategory.getCategoryType()).isEqualTo(categoryType);
        assertThat(newCategory.getName()).isEqualTo("미용실");
    }
}
