package com.interaiview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interaiview.interaiview.ai.openai.OpenAIChatClient;
import com.interaiview.interaiview.dto.InterviewResultDTO;
import com.interaiview.interaiview.dto.QaPairsDTO;
import com.interaiview.interaiview.service.AIService;
import com.interaiview.interaiview.util.FileProcessorFactory;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AIService.class, FileProcessorFactory.class, OpenAIChatClient.class})
public class AIServiceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AIService aiService;

    @Autowired
    public AIServiceTest(AIService aiService) {
        this.aiService = aiService;
    }

    @MockitoSpyBean
    private OpenAIChatClient client;

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

    // _response.json파일로 mocking return
    @NotNull
    private static ChatCompletion getChatcompletion(String path) throws IOException {
        File file = new ClassPathResource(path).getFile();
        return objectMapper.readValue(file, ChatCompletion.class);
    }

    @Test
    public void generateQuestionTest() throws IOException {
        MultipartFile multipartFile = getMultipartFile("testdata/word_sample.docx");
        doReturn(getChatcompletion("testdata/question_response.json"))
                .when(client).request(any(ChatCompletionCreateParams.class));

        QaPairsDTO questions = aiService.generateQuestion(multipartFile, "word");

        assertThat(questions.getQas().size()).isEqualTo(3);
        assertThat(questions.getQas().get(0).getQuestion()).contains("Can you describe a specific project");
    }

    @Test
    public void generateResultTest() throws IOException {
        File file = new ClassPathResource("testdata/qas_sample.json").getFile();
        doReturn(getChatcompletion("testdata/result_response.json"))
                .when(client).request(any(ChatCompletionCreateParams.class));

        QaPairsDTO qaPairsDTO = objectMapper.readValue(file, QaPairsDTO.class);

        InterviewResultDTO result = aiService.generateInterviewResult(qaPairsDTO);

        assertThat(result.getQas().size()).isEqualTo(2);
        assertThat(result.getQas().get(0).getFeedback()).contains("The answer is well-structured and highlights specific");
    }
}
