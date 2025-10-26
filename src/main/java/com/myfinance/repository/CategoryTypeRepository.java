package com.myfinance.repository;

import com.myfinance.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryTypeRepository extends JpaRepository<CategoryType, Integer> {

    Optional<CategoryType> findByName(String name);
}
