package com.sahilkhan.employee_performance_tracker.repository;

import com.sahilkhan.employee_performance_tracker.entity.Goal;
import com.sahilkhan.employee_performance_tracker.enums.GoalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    long countByReviewCycleIdAndStatus(Long reviewCycleId, GoalStatus status);
}
