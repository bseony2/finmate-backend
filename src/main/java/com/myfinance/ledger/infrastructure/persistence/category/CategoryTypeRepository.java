package com.myfinance.ledger.infrastructure.persistence.category;

import com.myfinance.ledger.domain.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

    Optional<CategoryType> findByName(String name);
}
