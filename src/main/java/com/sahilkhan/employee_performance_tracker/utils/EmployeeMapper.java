package com.sahilkhan.employee_performance_tracker.utils;

import com.sahilkhan.employee_performance_tracker.dto.request.EmployeeRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.EmployeeResponse;
import com.sahilkhan.employee_performance_tracker.entity.Employee;

public final class EmployeeMapper {

    private EmployeeMapper() {}

    public static EmployeeResponse toResponse(Employee employee) {
        return toResponse(employee, null);
    }

    public static EmployeeResponse toResponse(Employee employee, Double averageRating) {
        if (employee == null) {
            return null;
        }
        averageRating = averageRating != null ? Math.round(averageRating * 100.0) / 100.0 : 0.0;
        return EmployeeResponse.builder()
                .id(employee.getUuid())
                .name(employee.getName())
                .department(employee.getDepartment())
                .role(employee.getRole())
                .joiningDate(employee.getJoiningDate())
                .averageRating(averageRating)
                .build();
    }

    public static Employee toCreateEntity(EmployeeRequest request) {
        if (request == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setName(request.name().trim());
        employee.setDepartment(request.department().trim());
        employee.setRole(request.role().trim());
        employee.setJoiningDate(request.joiningDate());
        return employee;
    }
}
