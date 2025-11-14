package com.myfinance.controller;

import com.myfinance.dto.request.IncomeRequest;
import com.myfinance.dto.response.IncomeResponse;
import com.myfinance.service.IncomeService;
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
        IncomeResponse response = incomeService.createIncome(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 수입 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncome(@PathVariable Long id) {
        IncomeResponse response = incomeService.getIncome(id);
        return ResponseEntity.ok(response);
    }

    /**
     * 월별 수입 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<IncomeResponse>> getMonthlyIncomes(
            @RequestParam int year,
            @RequestParam int month
    ) {
        List<IncomeResponse> responses = incomeService.getMonthlyIncomes(year, month);
        return ResponseEntity.ok(responses);
    }

    /**
     * 수입 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(
            @PathVariable Long id,
            @RequestBody IncomeRequest request
    ) {
        IncomeResponse response = incomeService.updateIncome(id, request);
        return ResponseEntity.ok(response);
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