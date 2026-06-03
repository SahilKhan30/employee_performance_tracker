package com.sahilkhan.employee_performance_tracker.repository;

import com.sahilkhan.employee_performance_tracker.entity.PerformanceReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    @Query("SELECT r FROM PerformanceReview r JOIN FETCH r.reviewCycle WHERE r.employee.id = :employeeId")
    List<PerformanceReview> findByEmployeeIdWithCycle(@Param("employeeId") Long employeeId);

    @Query("SELECT r.employee.id FROM PerformanceReview r " +
           "WHERE r.reviewCycle.id = :cycleId " +
           "GROUP BY r.employee.id " +
           "ORDER BY AVG(r.rating) DESC, COUNT(r) DESC, r.employee.id ASC")
    List<Long> findTopPerformerEmployeeIds(@Param("cycleId") Long cycleId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM PerformanceReview r WHERE r.reviewCycle.id = :cycleId")
    Double getAverageRatingForCycle(@Param("cycleId") Long cycleId);
}
