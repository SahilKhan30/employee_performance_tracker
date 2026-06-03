package com.sahilkhan.employee_performance_tracker.service.impl;

import com.sahilkhan.employee_performance_tracker.dto.request.PerformanceReviewRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.PerformanceReviewResponse;
import com.sahilkhan.employee_performance_tracker.entity.Employee;
import com.sahilkhan.employee_performance_tracker.entity.PerformanceReview;
import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;
import com.sahilkhan.employee_performance_tracker.exception.ResourceNotFoundException;
import com.sahilkhan.employee_performance_tracker.repository.PerformanceReviewRepository;
import com.sahilkhan.employee_performance_tracker.repository.ReviewCycleRepository;
import com.sahilkhan.employee_performance_tracker.service.EmployeeService;
import com.sahilkhan.employee_performance_tracker.service.PerformanceReviewService;
import com.sahilkhan.employee_performance_tracker.utils.PerformanceReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository performanceReviewRepository;
    private final EmployeeService employeeService;
    private final ReviewCycleRepository reviewCycleRepository;

    @Override
    @Transactional
    public PerformanceReviewResponse createReview(PerformanceReviewRequest request) {
        Employee employee = employeeService.getEmployeeByUuid(request.employeeId());
        ReviewCycle cycle = reviewCycleRepository.findByUuid(request.reviewCycleId())
                .orElseThrow(() -> new ResourceNotFoundException("Review cycle not found with id: " + request.reviewCycleId()));

        PerformanceReview review = PerformanceReviewMapper.toCreateEntity(request, employee, cycle);

        PerformanceReview saved = performanceReviewRepository.save(review);
        return PerformanceReviewMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PerformanceReviewResponse> getReviewsForEmployee(UUID employeeId) {
        // Validate employee exists and get entity
        Employee employee = employeeService.getEmployeeByUuid(employeeId);

        List<PerformanceReview> reviews = performanceReviewRepository.findByEmployeeIdWithCycle(employee.getId());
        return reviews.stream()
                .map(PerformanceReviewMapper::toResponse)
                .collect(Collectors.toList());
    }
}
