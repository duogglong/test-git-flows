package com.ndl.trustviec.controller;

import com.ndl.trustviec.common.constants.ApiList;
import com.ndl.trustviec.config.annotation.Api;
import com.ndl.trustviec.dto.JobDTO;
import com.ndl.trustviec.dto.request.JobFilterRequest;
import com.ndl.trustviec.dto.request.SaveJobRequest;
import com.ndl.trustviec.dto.response.ApiResponse;
import com.ndl.trustviec.dto.response.JobFilterResponse;
import com.ndl.trustviec.dto.response.PageDataResponse;
import com.ndl.trustviec.dto.response.SaveJobResponse;
import com.ndl.trustviec.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(path = ApiList.API_V1 + "/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping(path = "/save")
    @PreAuthorize("hasAnyAuthority(T(com.ndl.trustviec.common.constants.Role).ENTERPRISE, " +
            "T(com.ndl.trustviec.common.constants.Role).RECRUITER)")
    public ResponseEntity<ApiResponse<SaveJobResponse>> save(@RequestBody SaveJobRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(jobService.save(request)));
    }

    @PostMapping(path = "/public/filter")
    public ResponseEntity<ApiResponse<PageDataResponse<JobFilterResponse>>> filter(@RequestBody JobFilterRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(jobService.filter(request)));
    }

    @GetMapping(path = "/public/details/{id}")
    public ResponseEntity<ApiResponse<JobDTO>> getJob(@PathVariable("id") long id) {
        return ResponseEntity.ok(ApiResponse.ok(jobService.getJobById(id)));
    }
}
