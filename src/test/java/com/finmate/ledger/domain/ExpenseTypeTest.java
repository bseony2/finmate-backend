package com.finmate.ledger.domain;

import com.finmate.ledger.domain.category.ExpenseType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExpenseType Enum 테스트")
class ExpenseTypeTest {

    @Test
    @DisplayName("코드값 0은 FIXED(고정지출)이다")
    void valueOf_0_returns_FIXED() {
        // given
        int code = 0;

        // when
        ExpenseType type = ExpenseType.valueOf(code);

        // then
        assertThat(type).isEqualTo(ExpenseType.FIXED);
        assertThat(type.getValue()).isZero();
        assertThat(type.getDescription()).isEqualTo("고정지출");
    }

    @Test
    @DisplayName("코드값 1은 VARIABLE(변동지출)이다")
    void valueOf_1_returns_VARIABLE() {
        // given
        int code = 1;

        // when
        ExpenseType type = ExpenseType.valueOf(code);

        // then
        assertThat(type).isEqualTo(ExpenseType.VARIABLE);
        assertThat(type.getValue()).isEqualTo(1);
        assertThat(type.getDescription()).isEqualTo("변동지출");
    }

    @Test
    @DisplayName("잘못된 코드값은 IllegalArgumentException을 던진다")
    void valueOf_invalid_code_throws_exception() {
        // given
        int invalidCode = 99;

        // when & then
        assertThatThrownBy(() -> ExpenseType.valueOf(invalidCode))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 지출 구분 코드");
    }

    @Test
    @DisplayName("fromJson 메서드는 valueOf와 동일하게 동작한다")
    void fromJson_works_same_as_valueOf() {
        // when
        ExpenseType fixed = ExpenseType.fromJson(0);
        ExpenseType variable = ExpenseType.fromJson(1);

        // then
        assertThat(fixed).isEqualTo(ExpenseType.FIXED);
        assertThat(variable).isEqualTo(ExpenseType.VARIABLE);
    }

    @Test
    @DisplayName("모든 ExpenseType은 고유한 코드값을 가진다")
    void all_types_have_unique_codes() {
        // given
        ExpenseType[] types = ExpenseType.values();

        // when & then
        assertThat(types).hasSize(2);
        assertThat(types[0].getValue()).isNotEqualTo(types[1].getValue());
    }
}