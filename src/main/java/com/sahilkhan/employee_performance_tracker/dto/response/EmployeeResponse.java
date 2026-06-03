package com.sahilkhan.employee_performance_tracker.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private UUID id;
    private String name;
    private String department;
    private String role;
    private LocalDate joiningDate;
}
