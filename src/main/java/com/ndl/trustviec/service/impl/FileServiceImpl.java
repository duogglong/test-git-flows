package com.ndl.trustviec.service.impl;

import com.ndl.trustviec.common.error.ErrorConstants;
import com.ndl.trustviec.common.exception.CommonException;
import com.ndl.trustviec.config.SupabaseConfig;
import com.ndl.trustviec.dto.response.UploadFileResponse;
import com.ndl.trustviec.entity.FileEntity;
import com.ndl.trustviec.integration.dto.response.SignedUrlSupabaseResponse;
import com.ndl.trustviec.integration.dto.response.UploadFileSupabaseResponse;
import com.ndl.trustviec.integration.service.SupabaseStorageService;
import com.ndl.trustviec.repository.FileRepository;
import com.ndl.trustviec.service.FileService;
import com.ndl.trustviec.utils.DateUtils;
import com.ndl.trustviec.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import static com.ndl.trustviec.utils.FileUtils.*;

@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final SupabaseStorageService supabaseStorageService;
    private final SupabaseConfig supabaseConfig;
    private final FileRepository fileRepository;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Override
    public UploadFileResponse uploadFilePost(MultipartFile file, String folderType) {
        // Validate file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw CommonException.create(HttpStatus.BAD_REQUEST)
                    .code(ErrorConstants.INVALID_FILE_SIZE)
                    .withMessageParams(Collections.singletonList(maxFileSize));
        }

        // Validate file extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !hasValidImageExtension(originalFilename)) {
            throw CommonException.create(HttpStatus.BAD_REQUEST)
                    .code(ErrorConstants.INVALID_FILE_EXTENSION)
                    .withMessageParams(ALLOWED_EXTENSIONS);
        }

        String extensionFileName = getFileExtension(file);
        String fileName = UUID.randomUUID() + "." + extensionFileName;

        UploadFileSupabaseResponse uploadFileSupabaseResponse = supabaseStorageService.uploadFile(file,
                supabaseConfig.getBucket(), folderType, fileName);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setCif("111111");
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(uploadFileSupabaseResponse.getKey());
        fileEntity.setFileType(extensionFileName);
        fileEntity.setFileSize(file.getSize());
        fileEntity.setOriginFileName(originalFilename);
        fileEntity = fileRepository.save(fileEntity);

        return UploadFileResponse.builder()
                .id(fileEntity.getId())
                .build();
    }

    @Override
    public byte[] getFile(UUID fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId).orElse(null);
        if (Objects.isNull(fileEntity)) {
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstants.DATA_IS_NOT_EXIST);
        }

        if (StringUtils.isBlank(fileEntity.getUrl()) || DateUtils.isBeforeToday(fileEntity.getExpireAt())) {
            log.info("{}: url is blank or expired", getClass().getSimpleName());

            // Sign url
            SignedUrlSupabaseResponse signedUrlSupabaseResponse = supabaseStorageService.getSignedUrl(fileEntity.getFilePath(), supabaseConfig.getEffectiveUrlTime());

            long effectiveTimeMillis = supabaseConfig.getEffectiveUrlTime();
            String signedUrl = supabaseConfig.getGetFileUri() + signedUrlSupabaseResponse.getSignedURL();

            fileEntity.setUrl(signedUrl);
            fileEntity.setExpireAt(LocalDateTime.now().plus(Duration.ofMillis(effectiveTimeMillis)));

            fileRepository.save(fileEntity);
        }

        return supabaseStorageService.getByteFile(fileEntity.getUrl());
    }
}
