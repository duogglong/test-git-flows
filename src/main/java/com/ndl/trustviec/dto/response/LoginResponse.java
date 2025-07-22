package com.ndl.trustviec.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;

    private String refreshToken;
}
