package com.sahilkhan.employee_performance_tracker.dto.response;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReviewResponse {
    private UUID id;
    private Integer rating;
    private String reviewerNotes;
    private Instant submittedAt;
    private ReviewCycleResponse reviewCycle;
}
