package com.ndl.trustviec.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "supabase.storage")
public class SupabaseConfig {
    private String baseUrl;
    private String apiKey;
    private String bucket;
    private String folderPost;
    private long effectiveUrlTime;
    private String getFileUri;
}
