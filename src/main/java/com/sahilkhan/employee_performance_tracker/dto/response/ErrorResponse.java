package com.sahilkhan.employee_performance_tracker.dto.response;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path,
    Map<String, String> errors
) {
    // Constructor for standard errors
    public ErrorResponse(int status, String error, String message, String path) {
        this(Instant.now(), status, error, message, path, null);
    }

    // Constructor for validation errors
    public ErrorResponse(int status, String error, String message, String path, Map<String, String> errors) {
        this(Instant.now(), status, error, message, path, errors);
    }
}
