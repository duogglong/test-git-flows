package com.ndl.trustviec.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ndl.trustviec.common.constants.ApiList;
import com.ndl.trustviec.config.annotation.Api;
import com.ndl.trustviec.dto.request.VerifyOtpRequest;
import com.ndl.trustviec.dto.response.ApiResponse;
import com.ndl.trustviec.dto.response.VerifyOtpResponse;
import com.ndl.trustviec.service.OtpTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(path = ApiList.API_V1 + "/otp-transaction")
@RequiredArgsConstructor
public class OtpTransactionController {
    private final OtpTransactionService otpTransactionService;

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<VerifyOtpResponse>> verify(@RequestBody VerifyOtpRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.ok(otpTransactionService.verifyOtp(request)));
    }
}
