package com.sahilkhan.employee_performance_tracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

@MappedSuperclass
public abstract class AuditEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void prePersist(){
        Instant now = Instant.now();
        createdAt=now;
        updatedAt=now;
    }

    @PreUpdate
    protected void preUpdate(){
        updatedAt = Instant.now();
    }

}
