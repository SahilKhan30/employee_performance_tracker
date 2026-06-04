package com.sahilkhan.employee_performance_tracker.utils;

import com.sahilkhan.employee_performance_tracker.dto.request.PerformanceReviewRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.PerformanceReviewResponse;
import com.sahilkhan.employee_performance_tracker.entity.Employee;
import com.sahilkhan.employee_performance_tracker.entity.PerformanceReview;
import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;

public final class PerformanceReviewMapper {

    private PerformanceReviewMapper() {}

    public static PerformanceReviewResponse toResponse(PerformanceReview review) {
        if (review == null) {
            return null;
        }
        return PerformanceReviewResponse.builder()
                .id(review.getUuid())
                .rating(review.getRating())
                .reviewerNotes(review.getReviewerNotes())
                .submittedAt(review.getSubmittedAt())
                .reviewCycle(ReviewCycleMapper.toResponse(review.getReviewCycle()))
                .build();
    }

    public static PerformanceReview toCreateEntity(PerformanceReviewRequest request, Employee employee, ReviewCycle cycle) {
        if (request == null) {
            return null;
        }
        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setReviewCycle(cycle);
        review.setRating(request.rating());
        review.setReviewerNotes(request.reviewerNotes() != null ? request.reviewerNotes().trim() : null);
        return review;
    }
}
