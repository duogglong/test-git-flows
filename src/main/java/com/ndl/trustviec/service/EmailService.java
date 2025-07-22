package com.ndl.trustviec.service;

import com.ndl.trustviec.dto.Email;
import com.ndl.trustviec.dto.request.SendEmailRequest;

public interface EmailService {
    void sendHtmlMail(Email email);

    void requestSendMail(SendEmailRequest sendEmailRequest);

}
