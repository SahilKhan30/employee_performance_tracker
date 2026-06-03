package com.sahilkhan.employee_performance_tracker.repository;

import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewCycleRepository extends JpaRepository<ReviewCycle, Long> {
    Optional<ReviewCycle> findByUuid(UUID uuid);

    boolean existsByName(String name);
}
