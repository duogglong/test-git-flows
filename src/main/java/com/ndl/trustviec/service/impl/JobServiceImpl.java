package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.dto.JobDTO;
import com.ndl.trustviec.dto.request.JobFilterRequest;
import com.ndl.trustviec.dto.request.SaveJobRequest;
import com.ndl.trustviec.dto.response.JobFilterResponse;
import com.ndl.trustviec.dto.response.PageDataResponse;
import com.ndl.trustviec.dto.response.SaveJobResponse;
import com.ndl.trustviec.entity.JobEntity;
import com.ndl.trustviec.repository.CommonConfigRepository;
import com.ndl.trustviec.repository.JobRepository;
import com.ndl.trustviec.service.JobService;
import com.ndl.trustviec.utils.PageableUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class JobServiceImpl extends BaseService implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(CommonConfigRepository commonConfigRepository, JobRepository jobRepository) {
        super(commonConfigRepository);
        this.jobRepository = jobRepository;
    }

    @Override
    public SaveJobResponse save(SaveJobRequest request) {
        log.info("{}: ---save job: {}", getClass().getSimpleName(), request);

        JobEntity jobEntity = new JobEntity();

        jobEntity.setJobTitle(request.getJobTitle());
        jobEntity.setDescription(request.getDescription());
        jobEntity.setSalary(request.getSalary());
        jobEntity.setSalaryMin(request.getSalaryMin());
        jobEntity.setSalaryMax(request.getSalaryMax());
        jobEntity.setCurrency(request.getCurrency());
        jobEntity.setLocation(request.getLocation());
        jobEntity.setExperience(request.getExperience());

        jobEntity = jobRepository.save(jobEntity);

        return SaveJobResponse.builder()
                .id(jobEntity.getId())
                .build();
    }

    @Override
    public PageDataResponse<JobFilterResponse> filter(JobFilterRequest request) {
        Pageable pageable = PageableUtils.of(request.getPage(), request.getSize(), request.getSorts(), false);

        Page<JobFilterResponse> jobs = jobRepository.filter(request.getKeyword(), pageable).map(JobFilterResponse::new);

        return PageDataResponse.of(jobs);
    }

    @Override
    public JobDTO getJobById(Long id) {
        // Get job
        JobEntity job = jobRepository.findById(id).orElse(null);
        if (Objects.isNull(job)) {
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.DATA_IS_NOT_EXIST);
        }
        // Get enterprise info


        JobDTO response = new JobDTO();
        response.setId(job.getId());
        response.setJobTitle(job.getJobTitle());
        response.setSalary(job.getSalary());
        response.setSalaryMin(job.getSalaryMin());
        response.setSalaryMax(job.getSalaryMax());
        response.setLocation(job.getLocation());
        response.setDescription(job.getDescription());
        response.setPositionLevel(job.getPositionLevel());
        response.setNumberOfVacancies(job.getNumberOfVacancies());
        response.setWorkingType(job.getWorkingType());
        return response;
    }

}
