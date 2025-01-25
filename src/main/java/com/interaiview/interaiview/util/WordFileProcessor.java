package com.interaiview.interaiview.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class WordFileProcessor implements FileProcessor{

    public WordFileProcessor() {
    }

    @Override
    public String extractText(MultipartFile file) throws IOException {
        try(InputStream inputStream = file.getInputStream();
            XWPFDocument document = new XWPFDocument(inputStream)) {

            StringBuilder text = new StringBuilder();
            document.getParagraphs().forEach(xwpfParagraph -> text.append(xwpfParagraph.getText()));
            return text.toString();
        }
    }
}
