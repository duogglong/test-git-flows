package com.ndl.trustviec.dto.request;

import com.ndl.trustviec.dto.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JobFilterRequest extends BaseFilter {
    private String keyword;
}
