package com.ndl.trustviec.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BaseFilter {
    private int page = 0;
    private int size = 20;
    private Map<String, String> sorts = new HashMap<>();
}
