package com.sahilkhan.employee_performance_tracker.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCycleSummaryResponse {
    private Double averageRating;
    private EmployeeResponse topPerformer;
    private Long completedGoalsCount;
    private Long missedGoalsCount;
}
