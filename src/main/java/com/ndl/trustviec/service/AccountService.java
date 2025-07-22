package com.ndl.trustviec.service;

import com.ndl.trustviec.dto.request.EnterpriseSignUpRequest;
import com.ndl.trustviec.dto.request.SignUpRequest;

public interface AccountService {
    void createAccount(SignUpRequest signUpRequest);

    void createEnterpriseAccount(EnterpriseSignUpRequest signUpRequest);

    boolean isExistedEmail(String email);
}
