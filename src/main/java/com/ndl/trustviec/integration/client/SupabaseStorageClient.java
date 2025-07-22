package com.ndl.trustviec.integration.client;

import com.ndl.trustviec.integration.dto.request.SignedUrlSupabaseRequest;
import com.ndl.trustviec.integration.dto.response.SignedUrlSupabaseResponse;
import com.ndl.trustviec.integration.dto.response.UploadFileSupabaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "supabaseStorageClient", url = "https://jmojdxyjbqqmswgvbekb.supabase.co")
public interface SupabaseStorageClient {
    @PostMapping("/storage/v1/object/{bucketName}/{folderName}/{fileName}")
    UploadFileSupabaseResponse uploadFile(
            @RequestHeader("Authorization") String authorization,
            @RequestBody byte[] fileBytes,
            @PathVariable("bucketName") String bucketName,
            @PathVariable("folderName") String folderName,
            @PathVariable("fileName") String fileName
    );

    @PostMapping("/storage/v1/object/sign/{filePath}")
    SignedUrlSupabaseResponse getSignedUrl(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("filePath") String filePath,
            @RequestBody SignedUrlSupabaseRequest signedUrlSupabaseRequest
            );

}
