package com.sahilkhan.employee_performance_tracker.controller;

import com.sahilkhan.employee_performance_tracker.dto.request.PerformanceReviewRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.PerformanceReviewResponse;
import com.sahilkhan.employee_performance_tracker.service.PerformanceReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class PerformanceReviewController {

    private final PerformanceReviewService performanceReviewService;

    @PostMapping
    public ResponseEntity<PerformanceReviewResponse> createReview(@Valid @RequestBody PerformanceReviewRequest request) {
        PerformanceReviewResponse review = performanceReviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }
}
