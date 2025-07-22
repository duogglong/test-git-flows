package com.ndl.trustviec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "Profession")
@Table(name = "profession")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionEntity extends AbstractAuditingEntity {
    @Column(name = "profession_name")
    private String professionName;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "total_job")
    private BigDecimal totalJob;

    @Column(name = "total_job_available")
    private BigDecimal totalJobAvailable;
}
