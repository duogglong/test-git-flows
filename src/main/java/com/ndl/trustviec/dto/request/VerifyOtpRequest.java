package com.ndl.trustviec.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VerifyOtpRequest {
    private UUID transactionId;
    private String otp;
}
