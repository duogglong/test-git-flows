package com.ndl.trustviec.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");

    public static boolean hasValidImageExtension(String filename) {
        String lower = filename.toLowerCase();
        return ALLOWED_EXTENSIONS.stream().anyMatch(lower::endsWith);
    }

    public static String getFileExtension(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return "";
        }

        String originalFileName = file.getOriginalFilename();
        int dotIndex = originalFileName.lastIndexOf('.');

        if (dotIndex != -1 && dotIndex < originalFileName.length() - 1) {
            return originalFileName.substring(dotIndex + 1).toLowerCase(); // Trả về không có dấu chấm, viết thường
        }

        return "";
    }
}
