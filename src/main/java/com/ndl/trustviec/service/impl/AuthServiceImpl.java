package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.common.type.EmailType;
import com.ndl.trustviec.dto.JwtTokenGenerate;
import com.ndl.trustviec.dto.JwtTokenResponse;
import com.ndl.trustviec.dto.request.EnterpriseSignUpRequest;
import com.ndl.trustviec.dto.request.LoginRequest;
import com.ndl.trustviec.dto.request.OtpTransactionRequest;
import com.ndl.trustviec.dto.request.SignUpRequest;
import com.ndl.trustviec.dto.response.EnterpriseSignUpResponse;
import com.ndl.trustviec.dto.response.LoginResponse;
import com.ndl.trustviec.dto.response.OtpTransactionResponse;
import com.ndl.trustviec.dto.response.SignUpResponse;
import com.ndl.trustviec.entity.AccountEntity;
import com.ndl.trustviec.entity.RoleEntity;
import com.ndl.trustviec.repository.AccountRepository;
import com.ndl.trustviec.service.AccountService;
import com.ndl.trustviec.service.AuthService;
import com.ndl.trustviec.service.OtpTransactionService;
import com.ndl.trustviec.utils.JwtUtils;
import com.ndl.trustviec.utils.ObjectMapperUtils;
import com.ndl.trustviec.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expire-time-access-token}")
    private long expireTimeAccessToken;

    @Value("${jwt.token.expire-time-refresh-token}")
    private long expireTimeRefreshToken;

    private final OtpTransactionService otpTransactionService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest request) {
        if (Objects.isNull(request)) {
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }
        try {
            log.info("---login: {}", request);
            request.setUsername(StringUtils.escapePath(request.getUsername()));
            AccountEntity accountEntity = accountRepository.findByUsername(request.getUsername());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (Objects.isNull(accountEntity)) {
                throw CommonException.create(HttpStatus.UNAUTHORIZED).code(ErrorConstants.UNAUTHORIZED);
            }

            if (StringUtils.isBlank(request.getPassword())) {
                log.warn("{}: password is null", getClass().getSimpleName());
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.PASSWORD_INVALID);
            }

            List<String> roles = new ArrayList<>();
            if (!CollectionUtils.isEmpty(accountEntity.getRoles())) {
                roles = accountEntity.getRoles().stream().map(RoleEntity::getRole).toList();
            }

            if (passwordEncoder.matches(request.getPassword(), accountEntity.getPassword())) {
                JwtTokenResponse jwtTokenResponse = jwtUtils.generateToken(JwtTokenGenerate.builder()
                        .cif(accountEntity.getCif())
                        .email(accountEntity.getEmail())
                        .roles(roles)
                        .build());
                return LoginResponse.builder()
                        .accessToken(jwtTokenResponse.getAccessToken())
                        .refreshToken(jwtTokenResponse.getRefreshToken())
                        .build();
            }
            return null;
        } catch (Exception ex) {
            log.error("Error login", ex);
            throw ex;
        }
    }

    @Override
    public SignUpResponse signUp(SignUpRequest request) {
        log.info("{}: signUp with request --> {}", getClass().getSimpleName(), request);

        // Validate
        validateSignUp(request);

        SignUpResponse response = new SignUpResponse();

        OtpTransactionRequest otpTransactionRequest = new OtpTransactionRequest();
        otpTransactionRequest.setEmail(request.getEmail());
        otpTransactionRequest.setType(EmailType.REGISTRATION);
        otpTransactionRequest.setRequestObject(ObjectMapperUtils.toJson(request));

        OtpTransactionResponse otpTransactionResponse = otpTransactionService.sendOtp(otpTransactionRequest);

        response.setOtpTransactionId(otpTransactionResponse.getTransactionId());

        return response;
    }

    @Override
    public EnterpriseSignUpResponse enterpriseSignUp(EnterpriseSignUpRequest request) {
        log.info("{}: enterpriseSignUp with request --> {}", getClass().getSimpleName(), request);

        // Validate
        validateEnterpriseSignUp(request);

        EnterpriseSignUpResponse response = new EnterpriseSignUpResponse();

        OtpTransactionRequest otpTransactionRequest = new OtpTransactionRequest();
        otpTransactionRequest.setEmail(request.getEmail());
        otpTransactionRequest.setType(EmailType.REGISTRATION_ENTERPRISE);
        otpTransactionRequest.setRequestObject(ObjectMapperUtils.toJson(request));

        OtpTransactionResponse otpTransactionResponse = otpTransactionService.sendOtp(otpTransactionRequest);

        response.setOtpTransactionId(otpTransactionResponse.getTransactionId());

        return response;
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

    private void validateSignUp(SignUpRequest request) {
        if (Objects.isNull(request)) {
            log.warn("{}: request is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }
        if (StringUtils.isBlank(request.getEmail()) || !StringUtils.isValidEmail(request.getEmail())) {
            log.warn("{}: email is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.EMAIL_INVALID);
        }
        // Validate duplicate email
        if (accountService.isExistedEmail(request.getEmail())) {
            log.warn("{}: email is duplicate", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.EMAIL_DUPLICATE);
        }

        if (StringUtils.isBlank(request.getUsername())) {
            log.warn("{}: username is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.USERNAME_INVALID);
        }
        if (StringUtils.isBlank(request.getPassword())) {
            log.warn("{}: password is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.PASSWORD_INVALID);
        }
    }

    private void validateEnterpriseSignUp(EnterpriseSignUpRequest request) {
        if (Objects.isNull(request)) {
            log.warn("{}: request is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }
        if (StringUtils.isBlank(request.getEmail()) || !StringUtils.isValidEmail(request.getEmail())) {
            log.warn("{}: email is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.EMAIL_INVALID);
        }
        // Validate duplicate email
        if (accountService.isExistedEmail((request.getEmail()))) {
            log.warn("{}: email is duplicate", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.EMAIL_DUPLICATE);
        }

        if (StringUtils.isBlank(request.getPassword())) {
            log.warn("{}: password is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.PASSWORD_INVALID);
        }
    }
}
