package com.ndl.trustviec.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ndl.trustviec.dto.request.OtpTransactionRequest;
import com.ndl.trustviec.dto.request.VerifyOtpRequest;
import com.ndl.trustviec.dto.response.OtpTransactionResponse;
import com.ndl.trustviec.dto.response.VerifyOtpResponse;

public interface OtpTransactionService {
    OtpTransactionResponse sendOtp(OtpTransactionRequest request);

    VerifyOtpResponse verifyOtp(VerifyOtpRequest request) throws JsonProcessingException;
}
