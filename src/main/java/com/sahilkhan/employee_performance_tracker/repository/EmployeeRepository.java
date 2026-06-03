package com.sahilkhan.employee_performance_tracker.repository;

import com.sahilkhan.employee_performance_tracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUuid(UUID uuid);

    @Query("SELECT e FROM Employee e " +
           "LEFT JOIN PerformanceReview r ON r.employee = e " +
           "WHERE (:department IS NULL OR LOWER(e.department) = LOWER(CAST(:department AS string))) " +
           "GROUP BY e " +
           "HAVING (:minRating IS NULL OR COALESCE(AVG(r.rating), 0.0) >= :minRating)")
    List<Employee> filterEmployees(@Param("department") String department, @Param("minRating") Double minRating);
}
