package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.entity.EnterpriseEntity;
import com.ndl.trustviec.repository.EnterpriseRepository;
import com.ndl.trustviec.service.EnterpriseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;

    @Override
    public EnterpriseEntity save(EnterpriseEntity enterprise) {
        return enterpriseRepository.save(enterprise);
    }
}
