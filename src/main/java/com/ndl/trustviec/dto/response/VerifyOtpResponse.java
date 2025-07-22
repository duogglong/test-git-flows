package com.ndl.trustviec.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VerifyOtpResponse {
    private UUID transactionId;
    private Boolean isValid;
}
