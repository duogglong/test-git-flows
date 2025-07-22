package com.ndl.trustviec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "Job")
@Table(name = "job")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "enterprise_id")
    private String enterpriseId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "description")
    private String description;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "salary_min")
    private BigDecimal salaryMin;

    @Column(name = "salary_max")
    private BigDecimal salaryMax;

    @Column(name = "currency")
    private String currency;

    @Column(name = "location")
    private String location;

    @Column(name = "experience")
    private String experience;

    @Column(name = "position_level")
    private String positionLevel;

    @Column(name = "number_of_vacancies")
    private Integer numberOfVacancies;

    @Column(name = "working_type")
    private String workingType;

    @CreatedDate
    @Column(name = "created_time", updatable = false)
    private Instant createdTime = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_time")
    private Instant lastModifiedTime = Instant.now();
}
