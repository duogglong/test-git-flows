package com.ndl.trustviec.dto.request;

import lombok.Data;

@Data
public class EnterpriseSignUpRequest {
    // Thông tin công ty
    private String enterpriseName;
    private String taxCode;
    private String address;
    private String website;
    private String summary;
    private String email;
    private String phoneNumber;

//    private String companyLogoUrl;

    // Thông tin người phụ trách
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonPhone;

    // Thông tin đăng nhập
    private String password;

    // (Optional) Giấy phép đăng ký kinh doanh
//    private String businessLicenseUrl;  // URL file upload
}
