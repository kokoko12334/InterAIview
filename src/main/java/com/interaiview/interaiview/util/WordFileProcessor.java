package com.interaiview.interaiview.util;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class WordFileProcessor implements FileProcessor{

    public WordFileProcessor() {
    }

    @Override
    public String extractText(File file) throws IOException {
        try(FileInputStream fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis)) {

            StringBuilder text = new StringBuilder();
            document.getParagraphs().forEach(xwpfParagraph -> text.append(xwpfParagraph.getText()));
            return text.toString();
        }
    }
}
