package com.sahilkhan.employee_performance_tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReviewCycleRequest(
    @NotBlank(message = "Review cycle name is required")
    String name,

    @NotNull(message = "Start date is required")
    LocalDate startDate,

    @NotNull(message = "End date is required")
    LocalDate endDate
) {}
