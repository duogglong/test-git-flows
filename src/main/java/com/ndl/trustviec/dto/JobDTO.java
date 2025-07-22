package com.ndl.trustviec.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobDTO {
    private Long id;
    private String jobTitle;
    private BigDecimal salary;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String location;
    private String description;
    private EnterpriseDTO enterpriseInfo;
    private String positionLevel;
    private Integer numberOfVacancies;
    private String workingType;
    private String testNhe;
}
