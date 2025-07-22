package com.ndl.trustviec.common.constants;

import java.util.List;

public final class ApiList {

    public static final String API_V1 = "/v1";

    public static final List<String> PUBLIC_URLS = List.of(
            "/v1/jobs/public/**",
            "/v1/auth/sign-up",
            "/swagger-ui/**",
            "/v3/api-docs/**",
//            "/api/v3/api-docs",
            "/v1/auth/login",
            "/v1/auth/enterprise/sign-up",
            "/v1/otp-transaction/verify"
    );
}
