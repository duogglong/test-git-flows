package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.dto.response.ProfessionResponse;
import com.ndl.trustviec.service.ProfessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ProfessionServiceImpl implements ProfessionService {

    @Override
    public List<ProfessionResponse> filter() {
        return null;
    }
}
