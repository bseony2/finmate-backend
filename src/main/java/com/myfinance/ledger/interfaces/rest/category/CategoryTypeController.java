package com.myfinance.ledger.interfaces.rest.category;

import com.myfinance.ledger.domain.category.CategoryType;
import com.myfinance.ledger.application.category.CategoryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category-types")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CategoryTypeController {

    private final CategoryTypeService categoryTypeService;

    /**
     * 모든 카테고리 타입 조회
     * GET /api/category-types
     */
    @GetMapping
    public ResponseEntity<List<CategoryType>> getAllCategoryTypes() {
        List<CategoryType> categoryTypes = categoryTypeService.getAllCategoryTypes();
        return ResponseEntity.ok(categoryTypes);
    }

    /**
     * 카테고리 타입 생성
     * POST /api/category-types
     * Request Body: { "name": "수입" }
     */
    @PostMapping
    public ResponseEntity<CategoryType> createCategoryType(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        CategoryType categoryType = categoryTypeService.createCategoryType(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryType);
    }

    /**
     * 카테고리 타입 삭제
     * DELETE /api/category-types/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryType(@PathVariable Long id) {
        categoryTypeService.deleteCategoryType(id);
        return ResponseEntity.noContent().build();
    }
}