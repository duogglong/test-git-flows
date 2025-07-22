package com.ndl.trustviec.controller;

import com.ndl.trustviec.common.constants.ApiList;
import com.ndl.trustviec.config.annotation.Api;
import com.ndl.trustviec.dto.request.EnterpriseSignUpRequest;
import com.ndl.trustviec.dto.request.LoginRequest;
import com.ndl.trustviec.dto.request.SignUpRequest;
import com.ndl.trustviec.dto.response.ApiResponse;
import com.ndl.trustviec.dto.response.EnterpriseSignUpResponse;
import com.ndl.trustviec.dto.response.LoginResponse;
import com.ndl.trustviec.dto.response.SignUpResponse;
import com.ndl.trustviec.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(path = ApiList.API_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.login(request)));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.signUp(request)));
    }

    @PostMapping("/enterprise/sign-up")
    public ResponseEntity<ApiResponse<EnterpriseSignUpResponse>> enterpriseSignUp(@RequestBody EnterpriseSignUpRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(authService.enterpriseSignUp(request)));
    }
}
