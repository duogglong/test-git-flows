package com.ndl.trustviec.integration.service.impl;

import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.config.SupabaseConfig;
import com.ndl.trustviec.integration.client.SupabaseStorageClient;
import com.ndl.trustviec.integration.dto.request.SignedUrlSupabaseRequest;
import com.ndl.trustviec.integration.dto.response.SignedUrlSupabaseResponse;
import com.ndl.trustviec.integration.dto.response.UploadFileSupabaseResponse;
import com.ndl.trustviec.integration.service.SupabaseStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupabaseStorageServiceImpl implements SupabaseStorageService {

    private final SupabaseStorageClient supabaseStorageClient;
    private final SupabaseConfig supabaseConfig;
    private final RestTemplate restTemplate;

    public UploadFileSupabaseResponse uploadFile(MultipartFile file, String bucketName, String folderName, String fileName) {
        String authorizationHeader = getTokenSupabaseStorage();

        UploadFileSupabaseResponse response;
        try {
            response = supabaseStorageClient.uploadFile(authorizationHeader, file.getBytes(), bucketName, folderName, fileName);
        } catch (Exception ex) {
            log.error("Error get SupabaseStorageServiceImpl.uploadFile", ex);
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public SignedUrlSupabaseResponse getSignedUrl(String filePath, long effectiveUrlTime) {
        String authorizationHeader = getTokenSupabaseStorage();

        SignedUrlSupabaseResponse response;
        try {
            response = supabaseStorageClient.getSignedUrl(authorizationHeader,
                    filePath,
                    SignedUrlSupabaseRequest.builder().expiresIn(effectiveUrlTime).build());
        } catch (Exception ex) {
            log.error("Error get SupabaseStorageServiceImpl.getSignedUrl", ex);
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public byte[] getByteFile(String url) {
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
            return response.getBody();
        } catch (Exception ex) {
            log.error("{}: Error get SupabaseStorageServiceImpl.getByteFile", getClass().getSimpleName(), ex);
            throw CommonException.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getTokenSupabaseStorage() {
        return "Bearer " + supabaseConfig.getApiKey();
    }
}