package com.interaiview.util;

import com.interaiview.interaiview.service.FileProcessService;
import com.interaiview.interaiview.util.FileProcessorFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FileProcessService.class, FileProcessorFactory.class})
public class FileProcessorTest {

    private final FileProcessService fileProcessService;

    @Autowired
    public FileProcessorTest(FileProcessService fileProcessService) {
        this.fileProcessService = fileProcessService;
    }

    // 파일을 InputStream으로 읽어 MockMultipartFile로 변환
    @NotNull
    private static MultipartFile getMultipartFile(String path) throws IOException {
        File file = new ClassPathResource(path).getFile();
        FileInputStream fileInputStream = new FileInputStream(file);
        return new MockMultipartFile(
                "file",                          // 파일 이름 (일반적으로 HTTP 파라미터 이름)
                file.getName(),                  // 원본 파일 이름
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // MIME 타입
                fileInputStream);
    }

    @Test
    void getTextFromWord() throws IOException {
        MultipartFile file = getMultipartFile("testdata/word_sample.docx");

        String text = fileProcessService.getText(file, "word");

        assertThat(text).contains("University of California");
        assertThat(text).contains("Directed global public relations campaigns for Fortune");
        assertThat(text).contains("ABC Charity Organization");
    }

    @Test
    void getTextFromPdf() throws IOException {
        MultipartFile file = getMultipartFile("testdata/pdf_sample.pdf");

        String text = fileProcessService.getText(file, "pdf");

        assertThat(text).contains("Developed and maintained accounting records for up to fifty bank accounts");
        assertThat(text).contains("Excel, PowerPoint) with basic knowledge of PeopleSoft.");
        assertThat(text).contains("Communications and Customer Relations");
    }
}
