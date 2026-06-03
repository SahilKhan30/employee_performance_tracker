package com.sahilkhan.employee_performance_tracker.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCycleResponse {
    private UUID id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
