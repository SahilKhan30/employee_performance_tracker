package com.sahilkhan.employee_performance_tracker.service;

import com.sahilkhan.employee_performance_tracker.dto.request.EmployeeRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.EmployeeResponse;
import com.sahilkhan.employee_performance_tracker.entity.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeRequest request);
    List<EmployeeResponse> filterEmployees(String department, Double minRating);
    Employee getEmployeeByUuid(UUID uuid);
    Employee getEmployeeById(Long id);
}
