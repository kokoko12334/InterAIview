package com.interaiview.interaiview.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileProcessorFactory {
    private final Map<String, FileProcessor> processorMap;

    public FileProcessorFactory() {
        processorMap = new HashMap<>();

        try {
            processorMap.put("pdf", new PdfFileProcessor());
            processorMap.put("word", new WordFileProcessor());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileProcessor getFileProcessor(String fileType) {
        FileProcessor fileProcessor = processorMap.get(fileType.toLowerCase());
        if (fileProcessor == null) {
            throw new IllegalArgumentException("unsupported file type: " + fileType);
        }
        return fileProcessor;
    }
}
