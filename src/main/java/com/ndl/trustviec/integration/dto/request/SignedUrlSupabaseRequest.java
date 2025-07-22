package com.ndl.trustviec.integration.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignedUrlSupabaseRequest {
    private long expiresIn;
}
