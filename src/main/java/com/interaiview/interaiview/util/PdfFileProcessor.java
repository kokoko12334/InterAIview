package com.interaiview.interaiview.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfFileProcessor implements FileProcessor{

    private final PDFTextStripper stripper = new PDFTextStripper();

    public PdfFileProcessor() throws IOException {
    }

    @Override
    public String extractText(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            return stripper.getText(document);
        }
    }
}
