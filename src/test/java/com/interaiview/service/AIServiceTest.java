package com.interaiview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interaiview.interaiview.ai.openai.OpenAIChatClient;
import com.interaiview.interaiview.dto.InterviewResultDTO;
import com.interaiview.interaiview.dto.QaPairsDTO;
import com.interaiview.interaiview.service.AIService;
import com.interaiview.interaiview.util.FileProcessorFactory;
import org.assertj.core.api.Assertions;
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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AIService.class, FileProcessorFactory.class, OpenAIChatClient.class})
public class AIServiceTest {

    private final AIService aiService;

    @Autowired
    public AIServiceTest(AIService aiService) {
        this.aiService = aiService;
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
    public void generateQuestionTest() throws IOException {
        MultipartFile multipartFile = getMultipartFile("testdata/word_sample.docx");

        QaPairsDTO questions = aiService.generateQuestion(multipartFile, "word");

        for (int i=0; i < questions.getQas().size(); i++) {
            System.out.println("####################");
            String q = questions.getQas().get(i).getQuestion();
            String a = questions.getQas().get(i).getAnswer();
            String f = questions.getQas().get(i).getFeedback();
            System.out.println("question: " + q);
            System.out.println("answer: " + a);
            System.out.println("feedback: " + f);
        }
        Assertions.assertThat(questions.getQas().size()).isEqualTo(5);
    }

    @Test
    public void generateResultTest() throws IOException {
        File file = new ClassPathResource("testdata/qas_sample.json").getFile();
        ObjectMapper objectMapper = new ObjectMapper();

//        try {
//            QaPairsDTO qaPairsDTO = objectMapper.readValue(file, QaPairsDTO.class);
//            InterviewResultDTO result = aiService.generateInterviewResult(qaPairsDTO);
//            for (int i=0; i < result.getQas().size(); i++) {
//                System.out.println("####################");
//                String q = result.getQas().get(i).getQuestion();
//                String a = result.getQas().get(i).getAnswer();
//                String f = result.getQas().get(i).getFeedback();
//                System.out.println("question: " + q);
//                System.out.println("answer: " + a);
//                System.out.println("feedback: " + f);
//            }
//            System.out.println("total: " + result.getResult());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
