package com.ndl.trustviec.dto.request;

import com.ndl.trustviec.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {
    private String mailTo;

    private String type;

    private Map<String, Object> variables;

    public Boolean isNull() {
        return StringUtils.isNull(mailTo) || StringUtils.isNull(type);
    }
}
