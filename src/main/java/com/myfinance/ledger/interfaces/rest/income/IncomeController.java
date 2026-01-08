package com.myfinance.ledger.interfaces.rest.income;

import com.myfinance.ledger.application.income.IncomeService;
import com.myfinance.ledger.application.income.dto.IncomeResult;
import com.myfinance.ledger.interfaces.rest.income.dto.IncomeRequest;
import com.myfinance.ledger.interfaces.rest.income.dto.IncomeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 수입 API 컨트롤러
 */
@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    /**
     * 수입 생성
     */
    @PostMapping
    public ResponseEntity<IncomeResponse> createIncome(@RequestBody IncomeRequest request) {
        IncomeResult result = incomeService.createIncome(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(IncomeResponse.from(result));
    }

    /**
     * 수입 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncome(@PathVariable Long id) {
        IncomeResult result = incomeService.getIncome(id);
        return ResponseEntity.ok(IncomeResponse.from(result));
    }

    /**
     * 월별 수입 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<IncomeResponse>> getMonthlyIncomes(
            @RequestParam int year,
            @RequestParam int month
    ) {
        List<IncomeResult> results = incomeService.getMonthlyIncomes(year, month);
        return ResponseEntity.ok(results.stream().map(IncomeResponse::from).toList());
    }

    /**
     * 수입 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(
            @PathVariable Long id,
            @RequestBody IncomeRequest request
    ) {
        IncomeResult result = incomeService.updateIncome(id, request.toCommand());
        return ResponseEntity.ok(IncomeResponse.from(result));
    }

    /**
     * 수입 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}