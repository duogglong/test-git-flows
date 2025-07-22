package com.ndl.trustviec.service;

import com.ndl.trustviec.dto.JobDTO;
import com.ndl.trustviec.dto.request.JobFilterRequest;
import com.ndl.trustviec.dto.request.SaveJobRequest;
import com.ndl.trustviec.dto.response.JobFilterResponse;
import com.ndl.trustviec.dto.response.PageDataResponse;
import com.ndl.trustviec.dto.response.SaveJobResponse;

public interface JobService {
    SaveJobResponse save(SaveJobRequest request);

    PageDataResponse<JobFilterResponse> filter(JobFilterRequest request);

    JobDTO getJobById(Long id);

}
