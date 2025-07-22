package com.ndl.trustviec.dto.response;

import com.ndl.trustviec.entity.JobEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class JobFilterResponse {
    private Long id;
    private String jobTitle;
    private String companyName;
    private String description;
    private BigDecimal salary;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String currency;
    private String location;
    private String imageUrl;

    public JobFilterResponse(JobEntity entity) {
        this.id = entity.getId();
        this.jobTitle = entity.getJobTitle();
//        this.companyName = entity.getCompanyName();
        this.description = entity.getDescription();
        this.salary = entity.getSalary();
        this.salaryMin = entity.getSalary();
        this.salaryMax = entity.getSalaryMax();
        this.location = entity.getLocation();
//        this.imageUrl = entity.getImageUrl();
        this.currency = entity.getCurrency();
    }
}
