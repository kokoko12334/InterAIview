package com.interaiview.interaiview.service;

import com.interaiview.interaiview.util.FileProcessor;
import com.interaiview.interaiview.util.FileProcessorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileProcessService {

    private final FileProcessorFactory fileProcessorFactory;

    public String getText(MultipartFile file, String fileType) throws IOException {
        FileProcessor processor = fileProcessorFactory.getFileProcessor(fileType);
        return processor.extractText(file);
    }
}
