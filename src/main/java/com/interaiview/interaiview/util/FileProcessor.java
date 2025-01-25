package com.interaiview.interaiview.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileProcessor {
    String extractText(MultipartFile file) throws IOException;
}
