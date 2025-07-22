package com.ndl.trustviec.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndl.trustviec.common.constants.SystemConfig;
import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.common.type.EmailType;
import com.ndl.trustviec.dto.Email;
import com.ndl.trustviec.dto.request.*;
import com.ndl.trustviec.dto.response.OtpTransactionResponse;
import com.ndl.trustviec.dto.response.VerifyOtpResponse;
import com.ndl.trustviec.entity.OtpTransactionEntity;
import com.ndl.trustviec.repository.CommonConfigRepository;
import com.ndl.trustviec.repository.OtpTransactionRepository;
import com.ndl.trustviec.service.AccountService;
import com.ndl.trustviec.service.EmailService;
import com.ndl.trustviec.service.OtpTransactionService;
import com.ndl.trustviec.utils.OtpGenerator;
import com.ndl.trustviec.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class OtpTransactionServiceImpl extends BaseService implements OtpTransactionService {
    private final OtpTransactionRepository otpTransactionRepository;
    private final EmailService emailService;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final ObjectMapper mapper = new ObjectMapper();
    private final AccountService accountService;

    public OtpTransactionServiceImpl(CommonConfigRepository commonConfigRepository,
                                     OtpTransactionRepository otpTransactionRepository, EmailService emailService,
                                     AccountService accountService) {
        super(commonConfigRepository);
        this.otpTransactionRepository = otpTransactionRepository;
        this.emailService = emailService;
        this.accountService = accountService;
    }

    @Override
    public OtpTransactionResponse sendOtp(OtpTransactionRequest request) {
        try {
            log.info("{}: sendOtp.request --> {}", getClass().getSimpleName(), request);
            if (Objects.isNull(request)) {
                log.warn("{}: request is null", getClass().getSimpleName());
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
            }
            // Validate
            if (Objects.isNull(request.getType())) {
                log.warn("{}: type is null", getClass().getSimpleName());
                throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
            }

            OtpTransactionEntity entity = OtpTransactionEntity.builder()
                    .cif(request.getCif())
                    .email(request.getEmail())
                    .otp(OtpGenerator.generateOTP())
                    .type(request.getType().getCode())
                    .requestObject(request.getRequestObject())
                    .build();
            otpTransactionRepository.save(entity);

            Map<String, Object> variables = new HashMap<>();
            variables.put("otp", entity.getOtp());

            Email email = Email.builder()
                    .mailTo(entity.getEmail())
                    .subject(request.getType().getSubjectVi())
                    .template(request.getType().getTemplateVi())
                    .variables(variables)
                    .build();

            executorService.submit(() -> emailService.sendHtmlMail(email));

            return OtpTransactionResponse.builder().transactionId(entity.getId()).build();
        } catch (Exception ex) {
            log.error("{}: Exception when sendOtp", getClass().getSimpleName(), ex);
            throw ex;
        }
    }

    @Override
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) throws JsonProcessingException {
        if (Objects.isNull(request)) {
            log.warn("{}: request is null", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }

        OtpTransactionEntity entity = otpTransactionRepository.findById(request.getTransactionId()).orElse(null);
        if (Objects.isNull(entity)) {
            log.warn("{}: transaction not found --> {}", getClass().getSimpleName(), request.getTransactionId());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.DATA_IS_NOT_EXIST);
        }
        validateOtp(request, entity);

        entity.setIsVerified(true);
        otpTransactionRepository.save(entity);

        handleVerifyOtpSuccess(entity);

        return VerifyOtpResponse.builder().transactionId(entity.getId()).isValid(true).build();
    }

    private void handleVerifyOtpSuccess(OtpTransactionEntity entity) throws JsonProcessingException {
        switch (EmailType.valueOf(entity.getType())) {
            case REGISTRATION:
                // Parse request
                SignUpRequest signUpRequest = mapper.readValue(entity.getRequestObject(), SignUpRequest.class);
                // Create new account
                accountService.createAccount(signUpRequest);
                break;
            case REGISTRATION_ENTERPRISE:
                // Parse request
                EnterpriseSignUpRequest enterpriseSignUpRequest = mapper.readValue(entity.getRequestObject(), EnterpriseSignUpRequest.class);
                // Create new account enterprise
                accountService.createEnterpriseAccount(enterpriseSignUpRequest);
                break;
            default:
                throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstants.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateOtp(VerifyOtpRequest request, OtpTransactionEntity entity) {
        if (Boolean.TRUE.equals(entity.getIsVerified())) {
            log.warn("{}: transaction is invalid because verified --> {}", getClass().getSimpleName(), request.getTransactionId());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.DATA_IS_NOT_EXIST);
        }

        int otpEffectiveTime;
        try {
            otpEffectiveTime = Integer.parseInt(getConfigByKey(SystemConfig.OTP_EFFECTIVE_TIME));
        } catch (Exception e) {
            log.error("Error get OTP_EFFECTIVE_TIME: {}", e.getMessage());
            otpEffectiveTime = 3;
        }
        Instant expireTime = entity.getCreatedTime().plus(Duration.ofMinutes(otpEffectiveTime));
        if (Instant.now().isAfter(expireTime)) {
            log.warn("{}: transaction is expired --> {}", getClass().getSimpleName(), request.getTransactionId());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.DATA_IS_NOT_EXIST);
        }

        if (StringUtils.isNotEquals(entity.getOtp(), request.getOtp())) {
            log.warn("{}: transaction is invalid --> {}", getClass().getSimpleName(), request.getTransactionId());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.OTP_INVALID);
        }
    }
}
