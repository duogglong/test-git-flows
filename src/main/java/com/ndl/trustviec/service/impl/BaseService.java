package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.entity.CommonConfigEntity;
import com.ndl.trustviec.repository.CommonConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BaseService {
    private final CommonConfigRepository commonConfigRepository;
    private static Map<String, String> COMMON_CONFIGS = null;

    public BaseService(CommonConfigRepository commonConfigRepository) {
        this.commonConfigRepository = commonConfigRepository;
    }

    protected String getConfigByKey(String key) {
        if (Objects.isNull(COMMON_CONFIGS)) {
            List<CommonConfigEntity> commonConfigEntities = commonConfigRepository.findAll();
            COMMON_CONFIGS = commonConfigEntities.stream().collect((Collectors.toMap(CommonConfigEntity::getKey, CommonConfigEntity::getValue)));
        }
        return COMMON_CONFIGS.get(key);
    }

}
