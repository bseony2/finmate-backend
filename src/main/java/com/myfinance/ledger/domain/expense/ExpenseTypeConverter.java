package com.myfinance.ledger.domain.expense;

import com.myfinance.ledger.domain.category.ExpenseType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * ExpenseType을 DB에 정수로 저장/조회하는 컨버터
 */
@Converter(autoApply = true)
public class ExpenseTypeConverter implements AttributeConverter<ExpenseType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ExpenseType attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public ExpenseType convertToEntityAttribute(Integer value) {
        return value != null ? ExpenseType.fromJson(value) : null;
    }
}