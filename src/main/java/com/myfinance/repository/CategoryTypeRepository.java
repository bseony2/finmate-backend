package com.myfinance.repository;

import com.myfinance.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {
}
