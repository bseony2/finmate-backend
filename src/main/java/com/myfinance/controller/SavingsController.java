package com.myfinance.controller;

import com.myfinance.dto.request.SavingsRequest;
import com.myfinance.dto.response.SavingsResponse;
import com.myfinance.service.SavingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/savings")
public class SavingsController {

    private final SavingsService savingsService;

    @PostMapping
    public ResponseEntity<SavingsResponse> createSavings(
            @Valid @RequestBody SavingsRequest request  // ← @Valid 추가!
    ) {
        SavingsResponse response = savingsService.createSavings(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsResponse> getSavings(@PathVariable Long id) {
        SavingsResponse response = savingsService.getSavingsById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<List<SavingsResponse>> getMonthlySavings(
            @PathVariable int year,
            @PathVariable int month
    ) {
        if (year < 1 || year > 9999) {
            throw new IllegalArgumentException("연도는 1-9999 사이여야 합니다");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("월은 1-12 사이여야 합니다");
        }

        List<SavingsResponse> responses = savingsService.getMonthlySavings(year, month);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsResponse> updateSavings(
            @PathVariable Long id,
            @Valid @RequestBody SavingsRequest request  // ← @Valid 추가!
    ) {
        SavingsResponse response = savingsService.updateSavings(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavings(@PathVariable Long id) {
        savingsService.deleteSavings(id);
        return ResponseEntity.noContent().build();
    }
}
