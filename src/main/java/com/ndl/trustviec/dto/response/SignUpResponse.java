package com.ndl.trustviec.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class SignUpResponse {
    private UUID otpTransactionId;
}
