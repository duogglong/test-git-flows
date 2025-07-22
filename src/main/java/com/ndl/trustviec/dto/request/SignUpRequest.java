package com.ndl.trustviec.dto.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String icType;
    private String icNumber;
}
