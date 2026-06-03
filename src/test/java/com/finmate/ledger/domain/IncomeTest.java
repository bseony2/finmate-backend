package com.finmate.ledger.domain;

import com.finmate.ledger.domain.category.Category;
import com.finmate.ledger.domain.category.CategoryType;
import com.finmate.ledger.domain.income.Income;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class IncomeTest {

    CategoryType categoryType;
    Category majorCategory;
    Category minorCategory;

    @BeforeEach
    void setUp() {
        categoryType = CategoryType.of("테스트");
        majorCategory = Category.createTopLevelCategory(categoryType, "테스트");
        minorCategory = Category.createSubCategory(categoryType, "테스트", majorCategory);
    }


    @Test
    @DisplayName("금액은 null일수 없다")
    void amount_never_null() {

        //given
        String content = "테스트";
        BigDecimal amount = null;

        //when & then
        assertThatThrownBy(() -> Income.of(LocalDate.now(), majorCategory, minorCategory, content, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("금액은 null일수 없습니다");
    }

    @Test
    @DisplayName("금액은 0보다 작거나 같아서는 안된다")
    void amount_must_be_over_then_zero() {

        //given
        String content = "테스트";
        BigDecimal amount = BigDecimal.ZERO;

        //when & then
        Assertions.assertThatThrownBy(() -> Income.of(LocalDate.now(), majorCategory, minorCategory, content, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("0보다 작거나 같을수 없습니다");

    }

}