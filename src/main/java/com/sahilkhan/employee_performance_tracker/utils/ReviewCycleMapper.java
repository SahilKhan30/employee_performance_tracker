package com.sahilkhan.employee_performance_tracker.utils;

import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleResponse;
import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;

public final class ReviewCycleMapper {

    private ReviewCycleMapper() {}

    public static ReviewCycleResponse toResponse(ReviewCycle cycle) {
        if (cycle == null) {
            return null;
        }
        return ReviewCycleResponse.builder()
                .id(cycle.getUuid())
                .name(cycle.getName())
                .startDate(cycle.getStartDate())
                .endDate(cycle.getEndDate())
                .build();
    }

    public static ReviewCycle toCreateEntity(String name, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        ReviewCycle cycle = new ReviewCycle();
        cycle.setName(name);
        cycle.setStartDate(startDate);
        cycle.setEndDate(endDate);
        return cycle;
    }
}
