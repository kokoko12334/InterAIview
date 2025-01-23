package com.interaiview.interaiview.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PdfFileProcessor implements FileProcessor{

    private final PDFTextStripper stripper = new PDFTextStripper();

    public PdfFileProcessor() throws IOException {
    }

    @Override
    public String extractText(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            return stripper.getText(document);
        }
    }
}
