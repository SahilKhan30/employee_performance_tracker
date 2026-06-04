package com.sahilkhan.employee_performance_tracker.repository;

import com.sahilkhan.employee_performance_tracker.entity.Employee;

public interface EmployeeWithRating {
    Employee getEmployee();
    Double getAverageRating();
}
