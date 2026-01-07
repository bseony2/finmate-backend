package com.myfinance.ledger.interfaces.rest.category;

import com.myfinance.ledger.application.category.dto.CategoryResult;
import com.myfinance.ledger.interfaces.rest.category.dto.CategoryRequest;
import com.myfinance.ledger.interfaces.rest.category.dto.CategoryResponse;
import com.myfinance.ledger.application.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 트리 조회
     * GET /api/categories?types=수입,지출
     * GET /api/categories?types=수입
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategoryTree(
            @RequestParam(name = "types") List<String> categoryTypeNames) {

        List<CategoryResult> results = categoryService.getCategoryTree(categoryTypeNames);
        return ResponseEntity.ok(results.stream().map(CategoryResponse::from).toList());
    }

    /**
     * 카테고리 생성
     * POST /api/categories
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        CategoryResult result = categoryService.createCategory(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryResponse.from(result));
    }

    /**
     * 카테고리 수정
     * PUT /api/categories/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {
        CategoryResult result = categoryService.updateCategory(id, request.toCommand());
        return ResponseEntity.ok(CategoryResponse.from(result));
    }

    /**
     * 카테고리 삭제
     * DELETE /api/categories/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}