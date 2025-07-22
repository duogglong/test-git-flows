package com.ndl.trustviec.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProfessionResponse {
    private String professionName;

    private String iconUrl;

    private BigDecimal totalJob;

    private BigDecimal totalJobAvailable;
}
