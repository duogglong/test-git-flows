package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.common.constants.Role;
import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.dto.request.EnterpriseSignUpRequest;
import com.ndl.trustviec.dto.request.SignUpRequest;
import com.ndl.trustviec.entity.AccountEntity;
import com.ndl.trustviec.entity.EnterpriseEntity;
import com.ndl.trustviec.entity.RoleEntity;
import com.ndl.trustviec.repository.AccountRepository;
import com.ndl.trustviec.repository.RoleRepository;
import com.ndl.trustviec.service.AccountService;
import com.ndl.trustviec.service.EnterpriseService;
import com.ndl.trustviec.utils.PasswordGenerator;
import com.ndl.trustviec.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final EnterpriseService enterpriseService;

    @Override
    public void createAccount(SignUpRequest request) {
        Optional<RoleEntity> role = roleRepository.findById(Role.CANDIDATE);
        if (role.isEmpty()) {
            log.error("Role {} not found", Role.CANDIDATE);
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstants.INTERNAL_SERVER_ERROR);
        }

        AccountEntity account = new AccountEntity();
        account.setCif(StringUtils.generateCIF());
        account.setUsername(request.getEmail());
        account.setEmail(request.getEmail());
//        account.setPhoneNumber(request.getPhoneNumber());
        account.setPassword(PasswordGenerator.hashPassword(request.getPassword()));
        account.setIsActive(true);
        account.setRoles(Collections.singleton(role.get()));

        accountRepository.save(account);
    }

    @Override
    public void createEnterpriseAccount(EnterpriseSignUpRequest request) {
        Optional<RoleEntity> role = roleRepository.findById(Role.ENTERPRISE);
        if (role.isEmpty()) {
            log.error("Role {} not found", Role.ENTERPRISE);
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstants.INTERNAL_SERVER_ERROR);
        }

        EnterpriseEntity enterprise = new EnterpriseEntity();
        enterprise.setTaxCode(request.getTaxCode());
        enterprise.setAddress(request.getAddress());
        enterprise.setWebsite(request.getWebsite());
        enterprise.setSummary(request.getSummary());
        enterprise.setEmail(request.getEmail());
        enterprise.setPhoneNumber(request.getPhoneNumber());
        enterprise.setContactPersonName(request.getContactPersonName());
        enterprise.setContactPersonEmail(request.getContactPersonEmail());
        enterprise.setContactPersonPhone(request.getContactPersonPhone());
        enterprise = enterpriseService.save(enterprise);

        AccountEntity account = new AccountEntity();
        account.setCif(StringUtils.generateCIF());
        account.setEnterpriseId(enterprise.getId());
        account.setUsername(request.getEmail());
        account.setEmail(request.getEmail());
        account.setPassword(PasswordGenerator.hashPassword(request.getPassword()));
        account.setIsActive(true);
        account.setRoles(Collections.singleton(role.get()));

        accountRepository.save(account);
    }

    @Override
    public boolean isExistedEmail(String email) {
        AccountEntity account = accountRepository.findByEmail(email);
        return Objects.nonNull(account);
    }
}
