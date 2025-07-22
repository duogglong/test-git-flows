package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.common.type.EmailType;
import com.ndl.trustviec.common.type.SendEmailType;
import com.ndl.trustviec.dto.Email;
import com.ndl.trustviec.dto.request.OtpTransactionRequest;
import com.ndl.trustviec.dto.request.SendEmailRequest;
import com.ndl.trustviec.dto.response.OtpTransactionResponse;
import com.ndl.trustviec.entity.OtpTransactionEntity;
import com.ndl.trustviec.repository.CommonConfigRepository;
import com.ndl.trustviec.service.EmailService;
import com.ndl.trustviec.utils.OtpGenerator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class EmailServiceImpl extends BaseService implements EmailService {
    private final JavaMailSenderImpl mailSender;
    private final Configuration freemarkerConfig;

    public EmailServiceImpl(CommonConfigRepository commonConfigRepository, JavaMailSenderImpl mailSender, Configuration freemarkerConfig) {
        super(commonConfigRepository);
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

//    public JavaMailSenderImpl getMailSender() {
//        mailSender.setUsername(getConfigByKey(SystemConfig.SYS_EMAIL));
//        mailSender.setPassword(getConfigByKey(SystemConfig.SYS_EMAIL_PASSWORD));
//        return mailSender;
//    }

    @Override
    public void sendHtmlMail(Email email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            Template template = freemarkerConfig.getTemplate(email.getTemplate());
            String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, email.getVariables());

            helper.setTo(email.getMailTo());
            helper.setSubject(email.getSubject());
            helper.setText(htmlContent, true);

            mailSender.send(message);
//            getMailSender().send(message);
        } catch (Exception e) {
            log.warn("{}: Exception --> ", getClass().getSimpleName(), e);
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR).code(ErrorConstants.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void requestSendMail(SendEmailRequest request) {
        if (Objects.isNull(request) || request.isNull()) {
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }
        log.info("{}: requestSendMail with request --> {}", getClass().getSimpleName(), request);

        String subject, template;
        switch (SendEmailType.getType(request.getType())) {
            case FIRST_PASSWORD -> {
                subject = EmailType.FIRST_PASSWORD.getSubjectVi();
                template = EmailType.FIRST_PASSWORD.getTemplateVi();
            }
            default -> throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }

        Email email = Email.builder()
                .mailTo(request.getMailTo())
                .subject(subject)
                .template(template)
                .variables(request.getVariables())
                .build();
        sendHtmlMail(email);
    }

    public void requestSendMail2(SendEmailRequest request) {
        if (Objects.isNull(request) || request.isNull()) {
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }
        log.info("{}: requestSendMail with request --> {}", getClass().getSimpleName(), request);

        String subject, template;
        switch (SendEmailType.getType(request.getType())) {
            case FIRST_PASSWORD -> {
                subject = EmailType.FIRST_PASSWORD.getSubjectVi();
                template = EmailType.FIRST_PASSWORD.getTemplateVi();
            }
            default -> throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }

        Email email = Email.builder()
                .mailTo(request.getMailTo())
                .subject(subject)
                .template(template)
                .variables(request.getVariables())
                .build();
        sendHtmlMail(email);
    }

    public OtpTransactionResponse sendOtp2(OtpTransactionRequest request) {
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

}
