package com.ndl.trustviec.service;

import com.ndl.trustviec.dto.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    UploadFileResponse uploadFilePost(MultipartFile file, String folderType);

    byte[] getFile(UUID fileId);

}
