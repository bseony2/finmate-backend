package com.myfinance.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SavingsTest {

    LocalDate savingDate;
    Category majorCategory;
    Category minorCategory;
    String acctNo;
    String content;
    BigDecimal amount;

    @BeforeEach
    void setUp() {
        CategoryType categoryType = CategoryType.of("테스트 카테고리");
        savingDate = LocalDate.of(2025, 12, 2);
        majorCategory = Category.of(categoryType,"저축성지출", null);
        minorCategory = Category.of(categoryType,"하나은행", majorCategory);
        acctNo = "110-123-456789";
        content = "비상금";
        amount = new BigDecimal("500000");
    }

    @Test
    @DisplayName("저축 생성 성공")
    void createSavings_Success() {
        // given

        // when
        Savings savings = Savings.of(
                savingDate,
                majorCategory,
                minorCategory,
                acctNo,
                content,
                amount
        );

        // then
        assertThat(savings).isNotNull();
        assertThat(savings.getSavingDate()).isEqualTo(savingDate);
        assertThat(savings.getMajorCategory()).isEqualTo(majorCategory);
        assertThat(savings.getMinorCategory()).isEqualTo(minorCategory);
        assertThat(savings.getAcctNo()).isEqualTo(acctNo);
        assertThat(savings.getContent()).isEqualTo(content);
        assertThat(savings.getAmount()).isEqualByComparingTo(amount);
    }

    @Test
    @DisplayName("저축 날짜가 null이면 예외 발생")
    void createSavings_WhenDateIsNull_ThrowsException() {
        // given
        savingDate = null;

        //when, then
        assertSavingsCreationThrowsException("저축일자는 null일수 없습니다");

    }

    @Test
    @DisplayName("금액값이 null이면 예외 발생")
    void createSavings_WhenAmountIsNull_ThrowsException() {
        // given
        amount = null;

        //when, then
        assertSavingsCreationThrowsException("금액값은 null일 수 없습니다");
    }

    @Test
    @DisplayName("금액값이 음수면 예외 발생")
    void createSavings_WhenAmountIsZeroOrNegative_ThrowsException() {
        // given
        amount = amount.negate();
        
        // when, then
        assertSavingsCreationThrowsException("금액값은 0이하일 수 없습니다.");
    }

    @Test
    @DisplayName("대분류 카테고리가 null이면 예외 발생")
    void createSavings_WhenMajorCategoryIsNull_ThrowsException() {
        // given
        majorCategory = null;

        //when, then
        assertSavingsCreationThrowsException("대분류 카테고리는 null일 수 없습니다.");
    }

    private void assertSavingsCreationThrowsException(String description) {
        assertThatThrownBy(() -> Savings.of(
                savingDate,
                majorCategory,
                minorCategory,
                acctNo,
                content,
                amount)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(description);
    }
}