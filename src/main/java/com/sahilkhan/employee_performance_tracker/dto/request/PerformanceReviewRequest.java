package com.sahilkhan.employee_performance_tracker.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record PerformanceReviewRequest(
    @NotNull(message = "Employee ID is required")
    UUID employeeId,

    @NotNull(message = "Review cycle ID is required")
    UUID reviewCycleId,

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating,

    String reviewerNotes
) {}
