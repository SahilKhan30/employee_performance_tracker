package com.sahilkhan.employee_performance_tracker.utils;

import com.sahilkhan.employee_performance_tracker.dto.request.EmployeeRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.EmployeeResponse;
import com.sahilkhan.employee_performance_tracker.entity.Employee;

public final class EmployeeMapper {

    private EmployeeMapper() {}

    public static EmployeeResponse toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeResponse.builder()
                .id(employee.getUuid())
                .name(employee.getName())
                .department(employee.getDepartment())
                .role(employee.getRole())
                .joiningDate(employee.getJoiningDate())
                .build();
    }

    public static Employee toCreateEntity(EmployeeRequest request) {
        if (request == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setDepartment(request.department());
        employee.setRole(request.role());
        employee.setJoiningDate(request.joiningDate());
        return employee;
    }
}
