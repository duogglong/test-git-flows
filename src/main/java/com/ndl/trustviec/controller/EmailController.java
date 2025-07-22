package com.ndl.trustviec.controller;

import com.ndl.trustviec.common.constants.ApiList;
import com.ndl.trustviec.config.annotation.Api;
import com.ndl.trustviec.dto.request.SendEmailRequest;
import com.ndl.trustviec.dto.response.ApiResponse;
import com.ndl.trustviec.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(path = ApiList.API_V1 + "/internal/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;


    @PostMapping
    public ResponseEntity<ApiResponse<HttpStatus>> sendEmail(@RequestBody SendEmailRequest request) {
        emailService.requestSendMail(request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK));
    }

}
