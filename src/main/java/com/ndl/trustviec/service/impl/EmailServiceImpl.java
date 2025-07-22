package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.common.type.EmailType;
import com.ndl.trustviec.common.type.SendEmailType;
import com.ndl.trustviec.dto.Email;
import com.ndl.trustviec.dto.request.SendEmailRequest;
import com.ndl.trustviec.repository.CommonConfigRepository;
import com.ndl.trustviec.service.EmailService;
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
        // switch (SendEmailType.getType(request.getType())) {
        //     case FIRST_PASSWORD -> {
        //         subject = EmailType.FIRST_PASSWORD.getSubjectVi();
        //         template = EmailType.FIRST_PASSWORD.getTemplateVi();
        //     }
        //     default -> throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        // }
        subject = EmailType.FIRST_PASSWORD.getSubjectVi();
        template = EmailType.FIRST_PASSWORD.getTemplateVi();
        if (StringUtils.isBlank(subject) || StringUtils.isBlank(template)) {
            log.warn("{}: subject or template is blank", getClass().getSimpleName());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.BAD_REQUEST);
        }

        Email email = Email.builder()
                .mailTo(request.getMailTo())
                .subject(subject)
                .template(template)
                .variables(request.getVariables())
                .build();
        sendHtmlMail(email);
    }

}
