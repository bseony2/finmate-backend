package com.finmate.ledger.infrastructure.persistence.category;

import com.finmate.ledger.domain.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoryTypeRepository extends JpaRepository<CategoryType, Long> {

    Optional<CategoryType> findByName(String name);
}
