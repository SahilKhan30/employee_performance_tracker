package com.sahilkhan.employee_performance_tracker.service;

import com.sahilkhan.employee_performance_tracker.dto.request.PerformanceReviewRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.PerformanceReviewResponse;

import java.util.List;
import java.util.UUID;

public interface PerformanceReviewService {
    PerformanceReviewResponse createReview(PerformanceReviewRequest request);
    List<PerformanceReviewResponse> getReviewsForEmployee(UUID employeeId);
}
