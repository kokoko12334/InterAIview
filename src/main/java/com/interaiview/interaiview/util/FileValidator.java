package com.interaiview.interaiview.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class FileValidator {
    private static final long MAX_FILE_SIZE = 1024L * 1024L; // 1MB
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("doc", "docx", "pdf");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/pdf",
            "application/haansoftdocx",
            "application/docx",
            "application/doc"
    );

    public static ResponseEntity<String> validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("File is empty. Please upload a valid file.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File size exceeds the 10MB limit. Please upload a smaller file.");
        }

        String fileName = file.getOriginalFilename();
        if (!isValidFileExtension(fileName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unsupported file type. Only .doc, .docx, and .pdf are allowed.");
        }

        String mimeType = file.getContentType();
        if (!isValidMimeType(mimeType)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid file MIME type. Please upload a valid file.");
        }
        return null; // null 반환 시 유효성 검사 통과로 간주
    }

    public static String getFileType(MultipartFile file) {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        return switch (fileExtension) {
            case "pdf" -> "pdf";
            case "doc", "docx" -> "word";
            default -> "Unknown";
        };
    }

    private static boolean isValidFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return false;
        }
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(fileExtension);
    }

    private static boolean isValidMimeType(String mimeType) {
        return ALLOWED_MIME_TYPES.contains(mimeType);
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}
