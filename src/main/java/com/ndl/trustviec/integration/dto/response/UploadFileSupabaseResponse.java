package com.ndl.trustviec.integration.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UploadFileSupabaseResponse {
    @JsonProperty("Key")
    private String key;

    @JsonProperty("Id")
    private String id;

}
