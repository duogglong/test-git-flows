package com.ndl.trustviec.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {
    private String mailTo;

    private String subject;

    private String template;

    private Map<String, Object> variables;
}
