package com.ndl.trustviec.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaveJobRequest {
    private String jobTitle;
    private String description;
    private BigDecimal salary;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String currency;
    private String location;
    private String experience;
}
