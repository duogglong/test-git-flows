package com.ndl.trustviec.integration.service;

import com.ndl.trustviec.integration.dto.response.SignedUrlSupabaseResponse;
import com.ndl.trustviec.integration.dto.response.UploadFileSupabaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SupabaseStorageService {
    UploadFileSupabaseResponse uploadFile(MultipartFile file, String bucketName, String folderName, String fileName);

    SignedUrlSupabaseResponse getSignedUrl(String filePath, long effectiveUrlTime);

    byte[] getByteFile(String url);

}
