package com.sahilkhan.employee_performance_tracker.controller;

import com.sahilkhan.employee_performance_tracker.dto.request.ReviewCycleRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleSummaryResponse;
import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleResponse;
import com.sahilkhan.employee_performance_tracker.service.ReviewCycleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cycles")
@RequiredArgsConstructor
public class ReviewCycleController {

    private final ReviewCycleService reviewCycleService;

    @PostMapping
    public ResponseEntity<ReviewCycleResponse> createReviewCycle(@Valid @RequestBody ReviewCycleRequest request) {
        ReviewCycleResponse created = reviewCycleService.createReviewCycle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<ReviewCycleSummaryResponse> getReviewCycleSummary(@PathVariable UUID id) {
        ReviewCycleSummaryResponse summary = reviewCycleService.getReviewCycleSummary(id);
        return ResponseEntity.ok(summary);
    }
}
