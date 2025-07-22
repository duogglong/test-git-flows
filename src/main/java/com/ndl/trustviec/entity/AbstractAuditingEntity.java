package com.ndl.trustviec.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Base abstract class for entities which will hold definitions for created, last modified
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdBy", "createdTime", "lastModifiedBy", "lastModifiedTime"}, allowGetters = true)
@Setter
@Getter
public abstract class  AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", nullable = false)
    private UUID id;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private Instant createdTime = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_time")
    private Instant lastModifiedTime = Instant.now();
}

