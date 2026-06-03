package com.finmate.ledger.interfaces.rest.savings;

import com.finmate.ledger.application.savings.dto.SavingsResult;
import com.finmate.ledger.interfaces.rest.savings.dto.SavingsRequest;
import com.finmate.ledger.interfaces.rest.savings.dto.SavingsResponse;
import com.finmate.ledger.application.savings.SavingsService;
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
        SavingsResult result = savingsService.createSavings(request.toCommand());
        return ResponseEntity.ok(SavingsResponse.from(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavingsResponse> getSavings(@PathVariable Long id) {
        SavingsResult result = savingsService.getSavingsById(id);
        return ResponseEntity.ok(SavingsResponse.from(result));
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

        List<SavingsResult> results = savingsService.getMonthlySavings(year, month);
        return ResponseEntity.ok(results.stream().map(SavingsResponse::from).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavingsResponse> updateSavings(
            @PathVariable Long id,
            @Valid @RequestBody SavingsRequest request  // ← @Valid 추가!
    ) {
        SavingsResult result = savingsService.updateSavings(id, request.toCommand());
        return ResponseEntity.ok(SavingsResponse.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavings(@PathVariable Long id) {
        savingsService.deleteSavings(id);
        return ResponseEntity.noContent().build();
    }
}
