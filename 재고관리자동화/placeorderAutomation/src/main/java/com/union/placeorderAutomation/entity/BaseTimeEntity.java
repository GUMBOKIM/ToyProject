package com.union.placeorderAutomation.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = LocalDateTime.now();
        if (this.updateAt == null) updateAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }
}