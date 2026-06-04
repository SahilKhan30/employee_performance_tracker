package com.sahilkhan.employee_performance_tracker.service.impl;

import com.sahilkhan.employee_performance_tracker.dto.request.EmployeeRequest;
import com.sahilkhan.employee_performance_tracker.dto.response.EmployeeResponse;
import com.sahilkhan.employee_performance_tracker.entity.Employee;
import com.sahilkhan.employee_performance_tracker.exception.ResourceNotFoundException;
import com.sahilkhan.employee_performance_tracker.repository.EmployeeRepository;
import com.sahilkhan.employee_performance_tracker.repository.EmployeeWithRating;
import com.sahilkhan.employee_performance_tracker.service.EmployeeService;
import com.sahilkhan.employee_performance_tracker.utils.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        Employee employee = EmployeeMapper.toCreateEntity(request);
        Employee saved = employeeRepository.save(employee);
        return EmployeeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> filterEmployees(String department, Double minRating) {
        List<EmployeeWithRating> results = employeeRepository.filterEmployees(department, minRating);
        return results.stream()
                .map(result -> EmployeeMapper.toResponse(result.getEmployee(), result.getAverageRating()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeByUuid(UUID uuid) {
        return employeeRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with uuid: " + uuid));
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with internal id: " + id));
    }
}
