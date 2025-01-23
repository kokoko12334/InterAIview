package com.interaiview.service;

import com.interaiview.interaiview.service.FileProcessService;
import com.interaiview.interaiview.util.FileProcessorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FileProcessService.class, FileProcessorFactory.class})
public class FileProcessServiceTest {

    private final FileProcessService fileProcessService;

    @Autowired
    public FileProcessServiceTest(FileProcessService fileProcessService) {
        this.fileProcessService = fileProcessService;
    }

    @Test
    void getTextFromWord() throws IOException {
        // ClassPathResource -> src/main/recources부터 시작
        Resource resource = new ClassPathResource("testdata/word_sample.docx");
        File wordFile = resource.getFile();

        String text = fileProcessService.getText(wordFile, "word");

        assertThat(text).contains("University of California");
        assertThat(text).contains("Directed global public relations campaigns for Fortune");
        assertThat(text).contains("ABC Charity Organization");
    }

    @Test
    void getTextFromPdf() throws IOException {

        Resource resource = new ClassPathResource("testdata/pdf_sample.pdf");
        File pdfFile = resource.getFile();

        String text = fileProcessService.getText(pdfFile, "pdf");

        assertThat(text).contains("Developed and maintained accounting records for up to fifty bank accounts");
        assertThat(text).contains("Excel, PowerPoint) with basic knowledge of PeopleSoft.");
        assertThat(text).contains("Communications and Customer Relations");
    }
}
