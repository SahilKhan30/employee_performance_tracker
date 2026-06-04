package com.sahilkhan.employee_performance_tracker.service.impl;

import com.sahilkhan.employee_performance_tracker.dto.request.ReviewCycleRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleSummaryResponse;
import com.sahilkhan.employee_performance_tracker.dto.response.EmployeeResponse;
import com.sahilkhan.employee_performance_tracker.dto.response.ReviewCycleResponse;
import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;
import com.sahilkhan.employee_performance_tracker.enums.GoalStatus;
import com.sahilkhan.employee_performance_tracker.exception.BadRequestException;
import com.sahilkhan.employee_performance_tracker.exception.ResourceNotFoundException;
import com.sahilkhan.employee_performance_tracker.repository.GoalRepository;
import com.sahilkhan.employee_performance_tracker.repository.PerformanceReviewRepository;
import com.sahilkhan.employee_performance_tracker.repository.ReviewCycleRepository;
import com.sahilkhan.employee_performance_tracker.repository.EmployeeWithRating;
import com.sahilkhan.employee_performance_tracker.service.ReviewCycleService;
import com.sahilkhan.employee_performance_tracker.utils.EmployeeMapper;
import com.sahilkhan.employee_performance_tracker.utils.ReviewCycleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewCycleServiceImpl implements ReviewCycleService {

    private final ReviewCycleRepository reviewCycleRepository;
    private final PerformanceReviewRepository performanceReviewRepository;
    private final GoalRepository goalRepository;

    @Override
    @Transactional(readOnly = true)
    public ReviewCycleSummaryResponse getReviewCycleSummary(UUID uuid) {
        ReviewCycle cycle = reviewCycleRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Review cycle not found with id: " + uuid));

        Long internalId = cycle.getId();

        Double avgRating = performanceReviewRepository.getAverageRatingForCycle(internalId);
        avgRating = avgRating != null ? Math.round(avgRating * 100.0) / 100.0 : 0.0;

        List<EmployeeWithRating> topPerformers = performanceReviewRepository.findTopPerformer(internalId, PageRequest.of(0, 1));
        EmployeeResponse topPerformer = null;
        if (!topPerformers.isEmpty()) {
            EmployeeWithRating projection = topPerformers.get(0);
            topPerformer = EmployeeMapper.toResponse(projection.getEmployee(), projection.getAverageRating());
        }

        long completedGoals = goalRepository.countByReviewCycleIdAndStatus(internalId, GoalStatus.COMPLETED);
        long missedGoals = goalRepository.countByReviewCycleIdAndStatus(internalId, GoalStatus.MISSED);

        return ReviewCycleSummaryResponse.builder()
                .averageRating(avgRating)
                .topPerformer(topPerformer)
                .completedGoalsCount(completedGoals)
                .missedGoalsCount(missedGoals)
                .build();
    }

    @Override
    @Transactional
    public ReviewCycleResponse createReviewCycle(ReviewCycleRequest request) {
        if (request.endDate().isBefore(request.startDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }
        String trimmedName = request.name() != null ? request.name().trim() : null;
        if (reviewCycleRepository.existsByName(trimmedName)) {
            throw new BadRequestException("Review cycle with name '" + trimmedName + "' already exists");
        }
        ReviewCycle cycle = ReviewCycleMapper.toCreateEntity(request);
        ReviewCycle saved = reviewCycleRepository.save(cycle);
        return ReviewCycleMapper.toResponse(saved);
    }
}
