package com.ndl.trustviec.service;

import com.ndl.trustviec.dto.request.EnterpriseSignUpRequest;
import com.ndl.trustviec.dto.request.LoginRequest;
import com.ndl.trustviec.dto.request.SignUpRequest;
import com.ndl.trustviec.dto.response.EnterpriseSignUpResponse;
import com.ndl.trustviec.dto.response.LoginResponse;
import com.ndl.trustviec.dto.response.SignUpResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    SignUpResponse signUp(SignUpRequest request);

    EnterpriseSignUpResponse enterpriseSignUp(EnterpriseSignUpRequest request);

}
