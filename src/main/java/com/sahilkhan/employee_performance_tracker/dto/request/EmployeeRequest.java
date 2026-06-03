package com.sahilkhan.employee_performance_tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;


public record EmployeeRequest (
    @NotBlank(message = "Name is required")
    String name,

    @NotBlank(message = "Department is required")
    String department,

    @NotBlank(message = "Role is required")
    String role,

    @NotNull(message = "Joining date is required")
    @PastOrPresent(message = "Joining date cannot be in the future")
    LocalDate joiningDate
){}

