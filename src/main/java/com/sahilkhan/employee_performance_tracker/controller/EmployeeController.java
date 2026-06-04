package com.sahilkhan.employee_performance_tracker.controller;

import com.sahilkhan.employee_performance_tracker.dto.request.EmployeeRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.EmployeeResponse;
import com.sahilkhan.employee_performance_tracker.dto.response.PerformanceReviewResponse;
import com.sahilkhan.employee_performance_tracker.service.EmployeeService;
import com.sahilkhan.employee_performance_tracker.service.PerformanceReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PerformanceReviewService performanceReviewService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) @Min(value = 0, message = "Minimum rating must be at least 0.0")
            @Max(value = 5, message = "Minimum rating must be at most 5.0") Double minRating) {
        List<EmployeeResponse> employees = employeeService.filterEmployees(department, minRating);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<PerformanceReviewResponse>> getEmployeeReviews(@PathVariable UUID id) {
        List<PerformanceReviewResponse> reviews = performanceReviewService.getReviewsForEmployee(id);
        return ResponseEntity.ok(reviews);
    }
}
