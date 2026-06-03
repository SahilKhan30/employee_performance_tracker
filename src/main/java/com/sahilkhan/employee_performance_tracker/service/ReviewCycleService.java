package com.sahilkhan.employee_performance_tracker.service;

import com.sahilkhan.employee_performance_tracker.dto.request.ReviewCycleRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleSummaryResponse;
import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleResponse;

import java.util.UUID;

public interface ReviewCycleService {
    ReviewCycleSummaryResponse getReviewCycleSummary(UUID uuid);
    ReviewCycleResponse createReviewCycle(ReviewCycleRequest request);
}
