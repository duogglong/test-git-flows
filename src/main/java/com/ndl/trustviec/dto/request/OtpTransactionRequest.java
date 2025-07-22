package com.ndl.trustviec.dto.request;

import com.ndl.trustviec.common.type.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpTransactionRequest {
    private String cif;

    private String email;

    private EmailType type;

    private String requestObject;

}
