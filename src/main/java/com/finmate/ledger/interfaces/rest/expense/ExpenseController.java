package com.finmate.ledger.interfaces.rest.expense;

import com.finmate.ledger.application.expense.ExpenseService;
import com.finmate.ledger.application.expense.dto.ExpenseResult;
import com.finmate.ledger.domain.category.ExpenseType;
import com.finmate.ledger.interfaces.rest.expense.dto.ExpenseRequest;
import com.finmate.ledger.interfaces.rest.expense.dto.ExpenseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * 지출 생성
     */
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody ExpenseRequest request) {
        ExpenseResult result = expenseService.createExpense(request.toCommand());
        return ResponseEntity.ok(ExpenseResponse.from(result));
    }

    /**
     * 지출 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request
    ) {
        ExpenseResult result = expenseService.updateExpense(id, request.toCommand());
        return ResponseEntity.ok(ExpenseResponse.from(result));
    }

    /**
     * 지출 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 특정 월의 지출 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getMonthlyExpenses(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam(required = false) Integer expenseType
    ) {
        ExpenseType type = expenseType != null ? ExpenseType.fromJson(expenseType) : null;
        List<ExpenseResult> result = expenseService.getMonthlyExpenses(year, month, type);
        return ResponseEntity.ok(result.stream().map(ExpenseResponse::from).toList());
    }

    /**
     * 지출 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable Long id) {
        ExpenseResult result = expenseService.getExpense(id);
        return ResponseEntity.ok(ExpenseResponse.from(result));
    }
}