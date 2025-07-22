package com.ndl.trustviec.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
    REGISTRATION("REGISTRATION", "Reta: Registration Account", "RETA: Đăng ký tài khoản", "otp-verify-sign-up-en.ftl", "otp-verify-sign-up-vi.ftl"),
    REGISTRATION_ENTERPRISE("REGISTRATION_ENTERPRISE", "Reta: Registration Account", "RETA: Đăng ký tài khoản", "otp-verify-sign-up-en.ftl", "otp-verify-sign-up-vi.ftl"),
    FIRST_PASSWORD("FIRST_PASSWORD", "Reta: Your first password", "RETA: Mật khẩu đăng nhập", "first-password-en.ftl", "first-password-vi.ftl");

    private final String code;
    private final String subjectEn;
    private final String subjectVi;
    private final String templateEn;
    private final String templateVi;

    public static EmailType fromCode(String code) {
        for (EmailType type : EmailType.values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid EmailType code: " + code);
    }
}
