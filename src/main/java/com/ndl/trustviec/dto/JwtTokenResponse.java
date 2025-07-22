package com.ndl.trustviec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtTokenResponse {

    private String accessToken;
    private String refreshToken;
}
