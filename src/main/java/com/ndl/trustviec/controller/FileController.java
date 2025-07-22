package com.ndl.trustviec.controller;

import com.ndl.trustviec.common.constants.ApiList;
import com.ndl.trustviec.config.SupabaseConfig;
import com.ndl.trustviec.config.annotation.Api;
import com.ndl.trustviec.dto.response.UploadFileResponse;
import com.ndl.trustviec.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Api(path = ApiList.API_V1 + "/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final SupabaseConfig supabaseConfig;


    @PostMapping("/upload/posts")
    public UploadFileResponse uploadFilePost(@RequestParam("file") MultipartFile file) {
        return fileService.uploadFilePost(file, supabaseConfig.getFolderPost());
    }

    @GetMapping("/{fileId}")
    public byte[] getFile(@PathVariable("fileId") UUID fileId) {
        return fileService.getFile(fileId);
    }
}
