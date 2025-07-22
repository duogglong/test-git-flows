package com.ndl.trustviec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtTokenGenerate {
    private String cif;
    private String enterpriseId;
    private String email;
    private List<String> roles;
}
