package com.finmate.ledger.domain.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

public enum ExpenseType {

    FIXED(0, "고정지출")
    , VARIABLE(1, "변동지출");

    private final int value;
    @Getter
    private final String description;

    ExpenseType(int type, String description) {
        this.value = type;
        this.description = description;
    }

    public static ExpenseType valueOf(int value) {
        return Arrays.stream(ExpenseType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 지출 구분 코드: " + value));
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static ExpenseType fromJson(int value) {
        return valueOf(value);
    }
}
